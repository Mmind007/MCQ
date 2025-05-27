package com.apexon.mcq.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apexon.mcq.dto.SkillDto;
import com.apexon.mcq.entity.Skill;
import com.apexon.mcq.repository.SkillRepository;
import com.apexon.mcq.utility.SkillMapper;

@Service
public class SkillService {
	
	@Autowired
    private SkillRepository skillRepository;

//    public SkillService(SkillRepository skillRepository) {
//        this.skillRepository = skillRepository;
//    }

    public SkillDto createSkill(Skill skill) {
        Skill skillRes =  skillRepository.save(skill);
        SkillDto skillDto = SkillMapper.toDto(skillRes);
        return skillDto;
    }

    public List<SkillDto> getAllSkills() {
    	List<Skill> skillList = skillRepository.findAll();
    	
    	List<SkillDto> dtoList = new ArrayList<>();
    	for(Skill skill : skillList) {
    		SkillDto skillDto = SkillMapper.toDto(skill);
    		dtoList.add(skillDto);
    	}
    	
    	return dtoList;
    }

	public SkillDto getSkillById(long id) {
		Skill skillRes = skillRepository.findById(id).orElse(null);
		SkillDto skillDto = SkillMapper.toDto(skillRes);
		return skillDto;
	}
}
