package com.apexon.mcq.service;

import com.apexon.mcq.entity.LearningProgress;
import com.apexon.mcq.entity.Question;
import com.apexon.mcq.exceptions.DuplicateProgressException;
import com.apexon.mcq.repository.LearningProgressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LearningProgressService {

    @Autowired
    private LearningProgressRepository progressRepository;

    public LearningProgress logProgress(LearningProgress progress) {
        // Prevent duplicate entry for same user-area-date
        if (progressRepository.existsByUserIdAndAreaIdAndDate(
                progress.getUser().getId(),
                progress.getArea().getId(),
                progress.getDate())) {
            throw new DuplicateProgressException("Progress for this area and date already logged.");
        }
        return progressRepository.save(progress);
    }

    public List<LearningProgress> getProgressByUser(Long userId) {
        return progressRepository.findByUserId(userId);
    }

    public List<LearningProgress> getProgressByUserAndArea(Long userId, Long areaId) {
        return progressRepository.findByUserIdAndAreaId(userId, areaId);
    }

    public Optional<LearningProgress> getLatestProgress(Long userId, Long areaId) {
        return progressRepository.findTopByUserIdAndAreaIdOrderByDateDesc(userId, areaId);
    }

    public List<LearningProgress> getProgressByDate(Long userId, LocalDate date) {
        return progressRepository.findByUserIdAndDate(userId, date);
    }

    public void deleteProgress(Long id) {
        progressRepository.deleteById(id);
    }

}
