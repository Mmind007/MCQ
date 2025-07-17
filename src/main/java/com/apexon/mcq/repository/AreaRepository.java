package com.apexon.mcq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apexon.mcq.entity.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
	
	List<Area> findBySkill_SkillId(Long skillId);
	//Go to the skill field of Area entity (which is a Skill object)
	//Then access the skillId field of that Skill
	//And compare it with the input

	boolean existsByNameIgnoreCase(String name);
}
