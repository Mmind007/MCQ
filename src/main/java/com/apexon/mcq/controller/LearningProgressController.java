package com.apexon.mcq.controller;

import com.apexon.mcq.dto.LearningProgressRequestDto;
import com.apexon.mcq.dto.LearningProgressResponseDto;
import com.apexon.mcq.entity.LearningProgress;
import com.apexon.mcq.service.LearningProgressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/progress")
@RestController
public class LearningProgressController {

    @Autowired
    private LearningProgressService progressService;

    @PostMapping
    public ResponseEntity<LearningProgressResponseDto> logProgress(@Valid @RequestBody LearningProgressRequestDto learningProgressRequestDto) {
        try {
            return ResponseEntity.ok(progressService.createProgress(learningProgressRequestDto));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/user/{userId}")
    public List<LearningProgressResponseDto> getProgressByUser(@PathVariable Long userId) {
        return progressService.getProgressByUser(userId);
    }

    // Progress by user + date range
    @GetMapping("/{userId}")
    public ResponseEntity<List<LearningProgressResponseDto>> getByUserAndDateRange(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate != null && endDate != null) {
            return ResponseEntity.ok(progressService.getProgressByUserAndDateRange(userId, startDate, endDate));
        } else {
            return ResponseEntity.ok(progressService.getProgressByUser(userId));
        }
    }

    // Progress summary per learning material
    @GetMapping("/summary/{userId}")
    public ResponseEntity<List<Object[]>> getSummaryPerMaterial(@PathVariable Long userId) {
        return ResponseEntity.ok(progressService.getProgressSummaryPerLearningMaterial(userId));
    }

    // Update entry
    @PutMapping("/{id}")
    public ResponseEntity<LearningProgressResponseDto> updateLearningProgress(@PathVariable Long id, @RequestBody LearningProgressRequestDto lp) {
        return ResponseEntity.ok(progressService.updateLearningProgress(id, lp));
    }

}
