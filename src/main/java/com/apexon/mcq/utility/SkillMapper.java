package com.apexon.mcq.utility;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.apexon.mcq.dto.AreaDto;
import com.apexon.mcq.dto.SkillDto;
import com.apexon.mcq.entity.Area;
import com.apexon.mcq.entity.Skill;

public class SkillMapper {

    public static SkillDto toSkillDto(Skill skill) {
        if (skill == null) return null;

        SkillDto dto = new SkillDto();
        dto.setName(skill.getSkillName());

        List<AreaDto> areaDtos = Optional.ofNullable(skill.getAreas())
        	    .orElse(Collections.emptyList())
        	    .stream()
        	    .map(SkillMapper::toAreaDto)
        	    .collect(Collectors.toList());

        dto.setAreas(areaDtos);

        return dto;
    }

    public static AreaDto toAreaDto(Area area) {
        if (area == null) return null;

        AreaDto dto = new AreaDto();
        dto.setName(area.getName());
        return dto;
    }
    
    // Convert SkillDto to Skill entity (used for create/update)
    public static Skill dtoToEntity(SkillDto dto) {
        if (dto == null) return null;

        Skill skill = new Skill();
        skill.setSkillName(dto.getName());

        // NOTE: We do NOT set Areas from DTO here,
        // because Areas list is not expected in client input during Skill creation.

        return skill;
    }
    
    public static Area dtoToEntity(AreaDto dto) {
        if (dto == null) return null;

        Area area = new Area();
        area.setName(dto.getName());

        return area;
    }
}
