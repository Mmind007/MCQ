package com.apexon.mcq.repository;

import com.apexon.mcq.entity.LearningProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningProgressRepository extends JpaRepository<LearningProgress, Long> {
    List<LearningProgress> findByUserIdOrderByDateAsc(Long userId);

    List<LearningProgress> findByUserIdAndAreaIdOrderByDateAsc(Long userId, Long areaId);
}
