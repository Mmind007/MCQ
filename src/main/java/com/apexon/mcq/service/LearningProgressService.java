package com.apexon.mcq.service;

import com.apexon.mcq.dto.LearningProgressRequestDto;
import com.apexon.mcq.dto.LearningProgressDto;
import com.apexon.mcq.dto.ProgressSummaryDTO;
import com.apexon.mcq.entity.Area;
import com.apexon.mcq.entity.LearningMaterial;
import com.apexon.mcq.entity.LearningProgress;
import com.apexon.mcq.entity.User;
import com.apexon.mcq.exceptions.BadRequestException;
import com.apexon.mcq.exceptions.OperationFailedException;
import com.apexon.mcq.exceptions.ResourceNotFoundException;
import com.apexon.mcq.repository.AreaRepository;
import com.apexon.mcq.repository.LearningMaterialRepository;
import com.apexon.mcq.repository.LearningProgressRepository;
import com.apexon.mcq.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LearningProgressService {

    @Autowired
    private LearningProgressRepository progressRepository;

    @Autowired
    private LearningMaterialRepository learningMaterialRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AreaRepository areaRepository;

    public LearningProgressDto createProgress(LearningProgressRequestDto learningProgressRequestDto) {

        try {

            User user = userRepository.findById(learningProgressRequestDto.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

            Area area = areaRepository.findById(learningProgressRequestDto.getArea())
                    .orElseThrow(() -> new ResourceNotFoundException("Area Not Found"));

            LearningMaterial learningMaterial = learningMaterialRepository.findById(learningProgressRequestDto.getLearningMaterial())
                    .orElseThrow(() -> new ResourceNotFoundException("Learning Material Not Available"));

            LearningProgress learningProgress = new LearningProgress();
            learningProgress.setCreatedAt(LocalDateTime.now());
            learningProgress.setUser(user);
            learningProgress.setArea(area);
            learningProgress.setLearningMaterial(learningMaterial);
            learningProgress.setSummary(learningProgressRequestDto.getSummary());
            learningProgress.setProgressPercentage(learningProgressRequestDto.getProgressPercentage());

            progressRepository.save(learningProgress);

            return LearningProgressDto.builder()
                    .learningProgressId(learningProgress.getId())
                    .progressPercentage(learningProgress.getProgressPercentage())
                    .user(user)
                    .summary(learningProgress.getSummary())
                    .localDate(learningProgress.getCreatedAt())
                    .learningMaterial(learningMaterial)
                    .build();


        } catch (BadRequestException | ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("Failed to save learning progress", ex);
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
                LearningProgressDto learningProgressResponseDto = convertToResponseDto(learningProgress);
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
                LearningProgressDto learningProgressResponseDto = convertToResponseDto(learningProgress);
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
            return new ProgressSummaryDTO(materialId, maxProgress,summary);
        }).toList();
    }

    // Update progress entry
    public LearningProgressDto updateLearningProgress(Long id, LearningProgressRequestDto updatedLp) {
        LearningProgress existinglp = progressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LearningProgress not found"));
        existinglp.setUpdatedAt(LocalDateTime.now());
        existinglp.setSummary(updatedLp.getSummary());
        existinglp.setProgressPercentage(updatedLp.getProgressPercentage());
        progressRepository.save(existinglp);

        return convertToResponseDto(existinglp);
    }

    public LearningProgressDto convertToResponseDto(LearningProgress learningProgress) {
        return LearningProgressDto.builder()
                .learningProgressId(learningProgress.getId())
                .user(learningProgress.getUser())
                .summary(learningProgress.getSummary())
                .localDate(learningProgress.getUpdatedAt() != null ?
                        learningProgress.getUpdatedAt() : learningProgress.getCreatedAt())
                .learningMaterial(learningProgress.getLearningMaterial())
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

            return progressList.stream().map(lp -> {
                LearningProgressDto dto = new LearningProgressDto();
                dto.setUser(lp.getUser());
                dto.setLearningMaterial(lp.getLearningMaterial());
                dto.setSummary(lp.getSummary());
                dto.setLocalDate(lp.getCreatedAt());
                dto.setProgressPercentage(lp.getProgressPercentage());
                return dto;
            }).collect(Collectors.toList());

        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("Failed to fetch learning history", ex);
        }
    }
}
