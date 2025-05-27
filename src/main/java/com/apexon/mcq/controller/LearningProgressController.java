package com.apexon.mcq.controller;

import com.apexon.mcq.entity.LearningProgress;
import com.apexon.mcq.service.LearningProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/progress")
@RestController
public class LearningProgressController {

    @Autowired
    private LearningProgressService progressService;

    @PostMapping
    public ResponseEntity<LearningProgress> create(@RequestBody LearningProgress progress) {
        return ResponseEntity.ok(progressService.createProgress(progress));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LearningProgress>> getTimeline(@PathVariable Long userId) {
        return ResponseEntity.ok(progressService.getProgressTimeline(userId));
    }

    @GetMapping("/user/{userId}/area/{areaId}")
    public ResponseEntity<List<LearningProgress>> getByArea(@PathVariable Long userId, @PathVariable Long areaId) {
        return ResponseEntity.ok(progressService.getProgressByArea(userId, areaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LearningProgress> update(@PathVariable Long id, @RequestBody LearningProgress progress) {
        return ResponseEntity.ok(progressService.updateProgress(id, progress));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        progressService.deleteProgress(id);
        return ResponseEntity.noContent().build();
    }
}
