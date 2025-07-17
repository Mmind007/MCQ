package com.apexon.mcq.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apexon.mcq.dto.SkillDto;
import com.apexon.mcq.entity.Skill;
import com.apexon.mcq.service.SkillService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/skillmgmt/skills")
public class SkillController {
	
	@Autowired
    private SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillDto> createSkill(@Valid @RequestBody SkillDto skillDto) {
        SkillDto createdSkill = skillService.createSkill(skillDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSkill);
    }

    @GetMapping
    public List<SkillDto> getAllSkills() {
        return skillService.getAllSkills();
    }
    
    @GetMapping("/{id}")
    public SkillDto getAllSkillById(@PathVariable long id) {
        return skillService.getSkillById(id);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SkillDto> updateSkill(@PathVariable long id, @Valid @RequestBody SkillDto skillDto) {
        SkillDto updated = skillService.updateSkill(id, skillDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
}