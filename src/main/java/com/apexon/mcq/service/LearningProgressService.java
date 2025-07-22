package com.apexon.mcq.service;

import com.apexon.mcq.dto.LearningProgressDto;
import com.apexon.mcq.dto.LearningProgressRequestDto;
import com.apexon.mcq.dto.ProgressSummaryDTO;
import com.apexon.mcq.entity.Area;
import com.apexon.mcq.entity.LearningMaterial;
import com.apexon.mcq.entity.LearningProgress;
import com.apexon.mcq.entity.User;
import com.apexon.mcq.exceptions.BadRequestException;
import com.apexon.mcq.exceptions.OperationFailedException;
import com.apexon.mcq.exceptions.ResourceNotFoundException;
import com.apexon.mcq.repository.AreaRepository;
import com.apexon.mcq.repository.LearningProgressRepository;
import com.apexon.mcq.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LearningProgressService {

    @Autowired
    private LearningProgressRepository progressRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    @Value("${area.service.url}")
    private String areaServiceUrl;

    @Value("${material.service.url}")
    private String materialServiceUrl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AreaRepository areaRepository;

    public LearningProgressDto createProgress(LearningProgressRequestDto learningProgressRequestDto) {

        try {
//            User user = fetchUserById(learningProgressRequestDto.getUser().getId());
            Area area = fetchAreaById(learningProgressRequestDto.getArea());
            boolean user = userRepository.existsById(learningProgressRequestDto.getUserId());

            if (!user) throw new ResourceNotFoundException("User Not Found");

            if (area==null)
                throw new ResourceNotFoundException("Area Not Found");


            LearningMaterial material = fetchLearningMaterialById(learningProgressRequestDto.getLearningMaterial());


            LearningProgress progress = new LearningProgress();
            progress.setUserId(learningProgressRequestDto.getUserId());
            progress.setAreaId(learningProgressRequestDto.getArea());
            progress.setLearningMaterialId(learningProgressRequestDto.getLearningMaterial());
            progress.setSummary(learningProgressRequestDto.getSummary());
            progress.setProgressPercentage(learningProgressRequestDto.getProgressPercentage());

            progressRepository.save(progress);
            return convertToResponseDto(progress, material);

        } catch (BadRequestException | ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("Failed to save learning progress", ex);
        }
    }

    private User fetchUserById(Long userId) {
        try {
            ResponseEntity<User> response = restTemplate.getForEntity(userServiceUrl + userId, User.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        } catch (Exception ex) {
            throw new OperationFailedException("Failed to fetch User from User service", ex);
        }
    }

    private Area fetchAreaById(Long areaId) {
        try {
            ResponseEntity<Area> response = restTemplate.getForEntity(areaServiceUrl + areaId, Area.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Area not found with id: " + areaId);
        } catch (Exception ex) {
            throw new OperationFailedException("Failed to fetch Area from Area service", ex);
        }
    }

    private LearningMaterial fetchLearningMaterialById(Long materialId) {
        try {
            ResponseEntity<LearningMaterial> response = restTemplate.getForEntity(materialServiceUrl + materialId, LearningMaterial.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Learning Material not found with id: " + materialId);
        } catch (Exception ex) {
            throw new OperationFailedException("Failed to fetch Learning Material from Material service", ex);
        }
    }


    // Get Progress By UserId
    public List<LearningProgressDto> getProgressByUser(Long userId) {

        try {

            List<LearningProgress> learningProgressList = progressRepository.findByUserId(userId);

            if (learningProgressList.isEmpty()) {
                throw new ResourceNotFoundException("No progress found for user: " + userId);
            }

            List<LearningProgressDto> learningProgressResponseDtoList = new ArrayList<>();

            for (LearningProgress learningProgress : learningProgressList) {
                LearningMaterial material = fetchLearningMaterialById(learningProgress.getLearningMaterialId());
                LearningProgressDto learningProgressResponseDto = convertToResponseDto(learningProgress, material);
                learningProgressResponseDtoList.add(learningProgressResponseDto);
            }

            return learningProgressResponseDtoList;
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("Error fetching user progress", ex);
        }
    }

    // Get progress by user and date range
    public List<LearningProgressDto> getProgressByUserAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {

        try {
            List<LearningProgress> learningProgressList = progressRepository.findByUserIdAndCreatedAtBetweenOrderByCreatedAtAsc(userId, startDate, endDate);

            if (learningProgressList.isEmpty()) {
                throw new ResourceNotFoundException("No progress found for user " + userId);
            }

            List<LearningProgressDto> learningProgressResponseDtoList = new ArrayList<>();

            for (LearningProgress learningProgress : learningProgressList) {
                LearningMaterial material = fetchLearningMaterialById(learningProgress.getLearningMaterialId());
                LearningProgressDto learningProgressResponseDto = convertToResponseDto(learningProgress, material);
                learningProgressResponseDtoList.add(learningProgressResponseDto);
            }
            return learningProgressResponseDtoList;
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("Error fetching progress", ex);
        }
    }

    // Get summary per learning material
    public List<ProgressSummaryDTO> getProgressSummaryPerLearningMaterial(Long userId) {
        List<Object[]> result = progressRepository.findProgressSummaryPerLearningMaterial(userId);

        return result.stream().map(row -> {
            Long materialId = (Long) row[0];
            Integer maxProgress = ((Number) row[1]).intValue();
            String summary = (String) row[2];
            return new ProgressSummaryDTO(materialId, maxProgress, summary);
        }).toList();
    }

    // Update progress entry
    public LearningProgressDto updateLearningProgress(Long id, LearningProgressRequestDto updatedLp) {
        LearningProgress existinglp = progressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LearningProgress not found"));
        LearningMaterial material = fetchLearningMaterialById(updatedLp.getLearningMaterial() != null ?
                updatedLp.getLearningMaterial() : existinglp.getLearningMaterialId());

        if (updatedLp.getProgressPercentage() != null) {
            existinglp.setProgressPercentage(updatedLp.getProgressPercentage());
        }

        if (updatedLp.getSummary() != null) {
            existinglp.setSummary(updatedLp.getSummary());
        }

        if (updatedLp.getProgressPercentage() != null)
            existinglp.setProgressPercentage(updatedLp.getProgressPercentage());
        try {
            LearningProgress saved = progressRepository.save(existinglp);

            return convertToResponseDto(saved, material);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("LearningMaterial not found with ID: " + id);
        } catch (Exception ex) {
            throw new OperationFailedException("Failed to fetch LearningMaterial", ex);
        }
    }

    public LearningProgressDto convertToResponseDto(LearningProgress learningProgress, LearningMaterial material) {
        return LearningProgressDto.builder()
                .learningProgressId(learningProgress.getId())
                .summary(learningProgress.getSummary())
                .localDate(learningProgress.getUpdatedAt() != null ?
                        learningProgress.getUpdatedAt() : learningProgress.getCreatedAt())
                .learningMaterial(material)
                .progressPercentage(learningProgress.getProgressPercentage())
                .build();

    }

    public List<LearningProgressDto> getHistoryForMaterial(Long userId, Long materialId) {

        try {
            List<LearningProgress> progressList = progressRepository
                    .findByUserIdAndLearningMaterialIdOrderByCreatedAtAsc(userId, materialId);

            if (progressList.isEmpty()) {
                throw new ResourceNotFoundException("No progress history found for userId: " + userId + ", materialId: " + materialId);
            }

            return progressList.stream().map(lp -> convertToResponseDto(lp, fetchLearningMaterialById(lp.getLearningMaterialId()))).collect(Collectors.toList());

        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("Failed to fetch learning history", ex);
        }
    }
}
