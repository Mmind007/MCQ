package com.apexon.mcq.repository;

import com.apexon.mcq.entity.LearningProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LearningProgressRepository extends JpaRepository<LearningProgress, Long> {
    List<LearningProgress> findByUserId(Long userId);

    List<LearningProgress> findByUserIdAndAreaId(Long userId, Long areaId);

    List<LearningProgress> findByUserIdAndDate(Long userId, LocalDate date);

    //range
    Optional<LearningProgress> findTopByUserIdAndAreaIdOrderByDateDesc(Long userId, Long areaId);

    boolean existsByUserIdAndAreaIdAndDate(Long userId, Long areaId, LocalDate date);
}
