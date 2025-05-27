package com.apexon.mcq.service;

import com.apexon.mcq.entity.LearningMaterial;
import com.apexon.mcq.repository.LearningMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LearningMaterialService {

    @Autowired
    private LearningMaterialRepository repository;

    public List<LearningMaterial> getAll() {

        return repository.findAll();
    }

    public LearningMaterial getById(Long id) {

        return repository.findById(id).orElse(null);
    }

    public LearningMaterial create(LearningMaterial material) {

        return repository.save(material);
    }

    public void delete(Long id) {

        repository.deleteById(id);
    }
}



