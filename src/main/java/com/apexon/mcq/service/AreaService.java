package com.apexon.mcq.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apexon.mcq.dto.AreaDto;
import com.apexon.mcq.entity.Area;
import com.apexon.mcq.entity.Skill;
import com.apexon.mcq.repository.AreaRepository;
import com.apexon.mcq.repository.SkillRepository;
import com.apexon.mcq.utility.SkillMapper;

@Service
public class AreaService {
	
	@Autowired
    private AreaRepository areaRepository;
	@Autowired
    private SkillRepository skillRepository;

    public AreaDto createArea(Long skillId, Area area) {
        Skill skill = skillRepository.findById(skillId)
            .orElseThrow(() -> new RuntimeException("Skill not found"));
        area.setSkill(skill);
        Area areaRes = areaRepository.save(area);
        AreaDto areaDto = SkillMapper.toAreaDto(areaRes);
        return areaDto;
    }

    public List<AreaDto> getAreasBySkill(Long skillId) {
    	List<Area> areaList = areaRepository.findAll().stream()
            .filter(area -> area.getSkill().getSkillId().equals(skillId))
            .collect(Collectors.toList());
    	
    	List<AreaDto> areaDtoList = new ArrayList<>();
    	for(Area area: areaList) {
    		AreaDto areaDto = SkillMapper.toAreaDto(area);
    		areaDtoList.add(areaDto);
    	}
    	
    	return areaDtoList;
    }
}
