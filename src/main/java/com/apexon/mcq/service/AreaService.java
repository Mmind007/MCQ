package com.apexon.mcq.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apexon.mcq.dto.AreaDto;
import com.apexon.mcq.entity.Area;
import com.apexon.mcq.entity.Skill;
import com.apexon.mcq.exceptions.SkillBadRequestException;
import com.apexon.mcq.exceptions.SkillResourceNotFoundException;
import com.apexon.mcq.repository.AreaRepository;
import com.apexon.mcq.repository.SkillRepository;
import com.apexon.mcq.utility.SkillMapper;

@Service
public class AreaService {
	
	@Autowired
    private AreaRepository areaRepository;
	@Autowired
    private SkillRepository skillRepository;

    public AreaDto createArea(Long skillId, AreaDto areaDto) {
        Skill skill = skillRepository.findById(skillId)
            .orElseThrow(() -> new SkillResourceNotFoundException("Skill with id " + skillId + " not found"));
       
        if (areaRepository.existsByNameIgnoreCase(areaDto.getName())) {
		    throw new SkillBadRequestException("Area already exists with name: " + areaDto.getName());
		}
        
        Area area = SkillMapper.dtoToEntity(areaDto);
        area.setSkill(skill);
        Area savedArea = areaRepository.save(area);
        AreaDto dto = SkillMapper.toAreaDto(savedArea);
        return dto;
    }

    public List<AreaDto> getAreasBySkill(Long skillId) {
    	List<Area> areaList = areaRepository.findAll().stream()
            .filter(area -> area.getSkill().getSkillId().equals(skillId))
            .collect(Collectors.toList());
    	
    	//OR
//    	List<Area> areaList = areaRepository.findBySkill_SkillId(skillId);
    	//Go to the skill field of Area entity (which is a Skill object)
    	//Then access the skillId field of that Skill
    	//And compare it with the input
    	
    	List<AreaDto> areaDtoList = new ArrayList<>();
    	for(Area area: areaList) {
    		AreaDto areaDto = SkillMapper.toAreaDto(area);
    		areaDtoList.add(areaDto);
    	}
    	
    	return areaDtoList;
    }
}
