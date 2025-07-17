package com.apexon.mcq.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class SkillDto {

	@NotBlank(message = "Skill name must not be empty")
    private String name;
    private List<AreaDto> areas;

    // Constructors
    public SkillDto() {}

    public SkillDto(String name, List<AreaDto> areas) {
        this.name = name;
        this.areas = areas;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaDto> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaDto> areas) {
        this.areas = areas;
    }
}
