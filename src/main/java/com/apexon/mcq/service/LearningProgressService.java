package com.apexon.mcq.service;

import com.apexon.mcq.dto.LearningProgressRequestDto;
import com.apexon.mcq.dto.LearningProgressResponseDto;
import com.apexon.mcq.entity.LearningMaterial;
import com.apexon.mcq.entity.LearningProgress;
import com.apexon.mcq.entity.Question;
import com.apexon.mcq.repository.LearningMaterialRepository;
import com.apexon.mcq.repository.LearningProgressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LearningProgressService {

    @Autowired
    private LearningProgressRepository progressRepository;

    @Autowired
    private LearningMaterialRepository learningMaterialRepository;

    public LearningProgressResponseDto createProgress(LearningProgressRequestDto learningProgressRequestDto) {

        LearningMaterial learningMaterial =
                learningMaterialRepository.findById(learningProgressRequestDto.getLearningMaterial())
                        .orElseThrow(()->new RuntimeException("Learning Material Not Available"));

        LearningProgress learningProgress = new LearningProgress();
        learningProgress.setDate(LocalDate.now());
        learningProgress.setLearningMaterial(learningMaterial);
        learningProgress.setSummary(learningProgress.getSummary());
        learningProgress.setUser(learningProgress.getUser());

        progressRepository.save(learningProgress);

        LearningProgressResponseDto learningProgressResponseDto = LearningProgressResponseDto.builder()
                .user(learningProgressRequestDto.getUser())
                .summary(learningProgress.getSummary())
                .localDate(learningProgress.getDate())
                .learningMaterial(learningProgress.getLearningMaterial())
                .build();

        return learningProgressResponseDto;
    }

    // Get Progress By UserId
    public List<LearningProgressResponseDto> getProgressByUser(Long userId) {

        List<LearningProgress> learningProgressList = progressRepository.findByUserId(userId);
        List<LearningProgressResponseDto> learningProgressResponseDtoList = new ArrayList<>();

        for(LearningProgress learningProgress : learningProgressList) {
            LearningProgressResponseDto learningProgressResponseDto = convertToResponseDto(learningProgress);
            learningProgressResponseDtoList.add(learningProgressResponseDto);
        }

        return learningProgressResponseDtoList;
    }

    // Get progress by user and date range
    public List<LearningProgressResponseDto> getProgressByUserAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {

        List<LearningProgress> learningProgressList =  progressRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId, startDate, endDate);
        List<LearningProgressResponseDto> learningProgressResponseDtoList = new ArrayList<>();

        for(LearningProgress learningProgress : learningProgressList) {
            LearningProgressResponseDto learningProgressResponseDto = convertToResponseDto(learningProgress);
            learningProgressResponseDtoList.add(learningProgressResponseDto);
        }
        return learningProgressResponseDtoList;
    }

    // Get summary per learning material
    public List<Object[]> getProgressSummaryPerLearningMaterial(Long userId) {
        return progressRepository.findProgressSummaryPerLearningMaterial(userId);
    }

    // Update progress entry
    public LearningProgressResponseDto updateLearningProgress(Long id, LearningProgressRequestDto updatedLp) {
        LearningProgress existinglp = progressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LearningProgress not found"));
        existinglp.setDate(LocalDate.now());
        existinglp.setSummary(updatedLp.getSummary());
        existinglp.setProgressPercentage(updatedLp.getProgressPercentage());
        // optionally update learning material or user if needed
        progressRepository.save(existinglp);

        return convertToResponseDto(existinglp);
    }

    public LearningProgressResponseDto convertToResponseDto(LearningProgress learningProgress)
    {
        return LearningProgressResponseDto.builder()
                .user(learningProgress.getUser())
                .summary(learningProgress.getSummary())
                .localDate(learningProgress.getDate())
                .learningMaterial(learningProgress.getLearningMaterial())
                .build();
    }
}
