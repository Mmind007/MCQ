package com.apexon.mcq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apexon.mcq.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
	
}