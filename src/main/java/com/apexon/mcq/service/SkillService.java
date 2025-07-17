package com.apexon.mcq.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apexon.mcq.dto.SkillDto;
import com.apexon.mcq.entity.Skill;
import com.apexon.mcq.exceptions.SkillBadRequestException;
import com.apexon.mcq.exceptions.SkillResourceNotFoundException;
import com.apexon.mcq.repository.SkillRepository;
import com.apexon.mcq.utility.SkillMapper;

@Service
public class SkillService {
	
	@Autowired
    private SkillRepository skillRepository;

	public SkillDto createSkill(SkillDto skillDto) {
		if (skillRepository.existsBySkillNameIgnoreCase(skillDto.getName())) {
		    throw new SkillBadRequestException("Skill already exists with name: " + skillDto.getName());
		}
		
        Skill skillEntity = SkillMapper.dtoToEntity(skillDto);
        // Note: areas list is empty here as we do not take it from client input
        Skill savedSkill = skillRepository.save(skillEntity);
        return SkillMapper.toSkillDto(savedSkill);
    }

    public List<SkillDto> getAllSkills() {
    	List<Skill> skillList = skillRepository.findAll();
    	
    	List<SkillDto> dtoList = new ArrayList<>();
    	for(Skill skill : skillList) {
    		SkillDto skillDto = SkillMapper.toSkillDto(skill);
    		dtoList.add(skillDto);
    	}
    	
    	return dtoList;
    }

	public SkillDto getSkillById(long id) {
		Skill skillRes = skillRepository.findById(id)
				.orElseThrow(() -> new SkillResourceNotFoundException("Skill with id " + id + " not found"));
		SkillDto skillDto = SkillMapper.toSkillDto(skillRes);
		return skillDto;
	}
	
	public SkillDto updateSkill(long id, SkillDto skillDto) {
	    Skill existingSkill = skillRepository.findById(id)
	            .orElseThrow(() -> new SkillResourceNotFoundException("Skill with id " + id + " not found"));

	    // Optional: check for name conflict with other skills
	    if (!existingSkill.getSkillName().equalsIgnoreCase(skillDto.getName())
	            && skillRepository.existsBySkillNameIgnoreCase(skillDto.getName())) {
	        throw new SkillBadRequestException("Another skill already exists with name: " + skillDto.getName());
	    }

	    existingSkill.setSkillName(skillDto.getName());
	    Skill updatedSkill = skillRepository.save(existingSkill);

	    return SkillMapper.toSkillDto(updatedSkill);
	}
	
	public void deleteSkill(long id) {
	    Skill existingSkill = skillRepository.findById(id)
	            .orElseThrow(() -> new SkillResourceNotFoundException("Skill with id " + id + " not found"));

	    skillRepository.delete(existingSkill);
	}


}
