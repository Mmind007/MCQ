package com.apexon.mcq.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apexon.mcq.dto.SkillDto;
import com.apexon.mcq.entity.Skill;
import com.apexon.mcq.service.SkillService;

@RestController
@RequestMapping("/skillmgmt/skills")
public class SkillController {
	
	@Autowired
    private SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillDto> createSkill(@RequestBody Skill skill) {
        SkillDto createdSkill = skillService.createSkill(skill);
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
    
//    @GetMapping("/greet")
//    public String greet() {
//        return "Hi, I'm Satish Kola. Welcome to my Project!!! <br><br> Please stay tuned for further updates :)";
//    }
}