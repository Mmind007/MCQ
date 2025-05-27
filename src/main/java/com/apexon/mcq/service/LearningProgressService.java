package com.apexon.mcq.service;

import com.apexon.mcq.entity.LearningProgress;
import com.apexon.mcq.entity.Question;
import com.apexon.mcq.repository.LearningProgressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LearningProgressService {

    @Autowired
    private LearningProgressRepository progressRepository;

    public LearningProgress createProgress(LearningProgress progress) {
        return progressRepository.save(progress);
    }

    public List<LearningProgress> getProgressTimeline(Long userId) {
        return progressRepository.findByUserIdOrderByDateAsc(userId);
    }

    public List<LearningProgress> getProgressByArea(Long userId, Long areaId) {
        return progressRepository.findByUserIdAndAreaIdOrderByDateAsc(userId, areaId);
    }

    public Optional<LearningProgress> getProgressById(Long id) {
        return progressRepository.findById(id);
    }

    public void deleteProgress(Long id) {
        progressRepository.deleteById(id);
    }

    public LearningProgress updateProgress(Long id, LearningProgress updatedProgress) {
        Question question = new Question();

        return progressRepository.findById(id).map(progress -> {
            progress.setDate(updatedProgress.getDate());
            progress.setArea(updatedProgress.getArea());
            progress.setSummary(updatedProgress.getSummary());
            progress.setProgressPercentage(updatedProgress.getProgressPercentage());
            return progressRepository.save(progress);
        }).orElseThrow(() -> new EntityNotFoundException("Progress not found"));
    }

}
