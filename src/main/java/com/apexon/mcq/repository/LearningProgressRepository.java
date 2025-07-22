package com.apexon.mcq.repository;

import com.apexon.mcq.entity.LearningProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LearningProgressRepository extends JpaRepository<LearningProgress, Long> {

    //progress logs for a user
    List<LearningProgress> findByUserId(Long userId);

    // Find progress by user within a date range
    List<LearningProgress> findByUserIdAndCreatedAtBetweenOrderByCreatedAtAsc(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    // Summary per learning material (latest progress per material)
    @Query("SELECT lp.learningMaterialId, MAX(lp.progressPercentage), lp.summary" +
            " FROM LearningProgress lp WHERE lp.userId = :userId GROUP BY lp.learningMaterialId")
    List<Object[]> findProgressSummaryPerLearningMaterial(@Param("userId") Long userId);


    // History for a material
    List<LearningProgress> findByUserIdAndLearningMaterialIdOrderByCreatedAtAsc(Long userId, Long learningMaterialId);


}
