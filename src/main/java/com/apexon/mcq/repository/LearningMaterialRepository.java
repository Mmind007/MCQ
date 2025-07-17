package com.apexon.mcq.repository;

import com.apexon.mcq.entity.LearningMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface LearningMaterialRepository extends JpaRepository<LearningMaterial, Long> {
}

