package com.apexon.mcq.controller;

import com.apexon.mcq.dto.LearningProgressRequestDto;
import com.apexon.mcq.dto.LearningProgressDto;
import com.apexon.mcq.dto.ProgressSummaryDTO;
import com.apexon.mcq.service.LearningProgressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequestMapping("/api/progress")
@RestController
public class LearningProgressController {

    @Autowired
    private LearningProgressService progressService;

    @PostMapping
    public ResponseEntity<LearningProgressDto> logProgress(@Valid @RequestBody LearningProgressRequestDto learningProgressRequestDto) {
        try {
            return ResponseEntity.ok(progressService.createProgress(learningProgressRequestDto));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/user/{userId}")
    public List<LearningProgressDto> getProgressByUser(@PathVariable Long userId) {
        return progressService.getProgressByUser(userId);
    }

    // Progress by user + date range
    @GetMapping("/{userId}")
    public ResponseEntity<List<LearningProgressDto>> getByUserAndDateRange(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate != null && endDate != null) {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

            return ResponseEntity.ok(progressService.getProgressByUserAndDateRange(userId, startDateTime, endDateTime));
        } else {
            return ResponseEntity.ok(progressService.getProgressByUser(userId));
        }
    }

    // Progress summary per learning material
    @GetMapping("/summary/{userId}")
    public ResponseEntity<List<ProgressSummaryDTO>> getSummaryPerMaterial(@PathVariable Long userId) {
        return ResponseEntity.ok(progressService.getProgressSummaryPerLearningMaterial(userId));
    }

    // Update entry
    @PutMapping("/{id}")
    public ResponseEntity<LearningProgressDto> updateLearningProgress(@PathVariable Long id,@Valid @RequestBody LearningProgressRequestDto lp) {
        return ResponseEntity.ok(progressService.updateLearningProgress(id, lp));
    }

    // progress history for a specific material by user
    @GetMapping("/history/{userId}/{learningMaterialId}")
    public ResponseEntity<List<LearningProgressDto>> getProgressHistoryForMaterial(
            @PathVariable Long userId,
            @PathVariable Long learningMaterialId) {

        return ResponseEntity.ok(progressService.getHistoryForMaterial(userId, learningMaterialId));
    }


}
