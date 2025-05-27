package com.apexon.mcq.controller;

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
    public ResponseEntity<LearningProgress> logProgress(@Valid @RequestBody LearningProgress progress) {
        try {
            return ResponseEntity.ok(progressService.logProgress(progress));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/user/{userId}")
    public List<LearningProgress> getByUser(@PathVariable Long userId) {
        return progressService.getProgressByUser(userId);
    }

    @GetMapping("/user/{userId}/area/{areaId}")
    public List<LearningProgress> getByUserAndArea(@PathVariable Long userId, @PathVariable Long areaId) {
        return progressService.getProgressByUserAndArea(userId, areaId);
    }

    @GetMapping("/user/{userId}/area/{areaId}/latest")
    public ResponseEntity<LearningProgress> getLatestProgress(@PathVariable Long userId, @PathVariable Long areaId) {
        return progressService.getLatestProgress(userId, areaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}/date/{date}")
    public List<LearningProgress> getByDate(@PathVariable Long userId,
                                            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return progressService.getProgressByDate(userId, date);
    }

    @DeleteMapping("/{id}")
    public void deleteProgress(@PathVariable Long id) {
        progressService.deleteProgress(id);
    }
}
