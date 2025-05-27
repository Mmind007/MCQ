package com.apexon.mcq.controller;

import com.apexon.mcq.entity.LearningMaterial;
import com.apexon.mcq.service.LearningMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class LearningMaterialController {

    @Autowired
    private LearningMaterialService service;

    @GetMapping
    public List<LearningMaterial> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public LearningMaterial getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public LearningMaterial create(@RequestBody LearningMaterial material) {
        return service.create(material);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
