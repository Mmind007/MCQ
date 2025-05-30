package com.apexon.mcq.repository;

import com.apexon.mcq.entity.LearningProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LearningProgressRepository extends JpaRepository<LearningProgress, Long> {

    List<LearningProgress> findByUserId(Long userId);

    // Find progress by user within a date range
    List<LearningProgress> findByUserIdAndDateBetweenOrderByDateAsc(Long userId, LocalDate startDate, LocalDate endDate);

    // Summary per learning material (latest progress per material)
    @Query("SELECT lp.learningMaterial.id, MAX(lp.progressPercentage) FROM LearningProgress lp " +
            "WHERE lp.user.id = :userId GROUP BY lp.learningMaterial.id")
    List<Object[]> findProgressSummaryPerLearningMaterial(@Param("userId") Long userId);

}
