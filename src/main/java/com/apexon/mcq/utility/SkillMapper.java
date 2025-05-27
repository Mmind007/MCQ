package com.apexon.mcq.utility;

import java.util.List;
import java.util.stream.Collectors;

import com.apexon.mcq.dto.AreaDto;
import com.apexon.mcq.dto.SkillDto;
import com.apexon.mcq.entity.Area;
import com.apexon.mcq.entity.Skill;



public class SkillMapper {

    public static SkillDto toDto(Skill skill) {
        if (skill == null) return null;

        SkillDto dto = new SkillDto();
        dto.setId(skill.getSkillId());
        dto.setName(skill.getSkillName());

        List<AreaDto> areaDtos = skill.getAreas().stream()
            .map(SkillMapper::toAreaDto)
            .collect(Collectors.toList());

        dto.setAreas(areaDtos);

        return dto;
    }

    public static AreaDto toAreaDto(Area area) {
        if (area == null) return null;

        AreaDto dto = new AreaDto();
        dto.setId(area.getId());
        dto.setName(area.getName());
        return dto;
    }
}
