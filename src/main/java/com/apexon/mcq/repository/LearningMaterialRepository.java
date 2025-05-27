package com.apexon.mcq.repository;

import com.apexon.mcq.entity.LearningMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningMaterialRepository extends JpaRepository<LearningMaterial, Long> {
}

