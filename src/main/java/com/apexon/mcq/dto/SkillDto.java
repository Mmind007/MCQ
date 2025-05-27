package com.apexon.mcq.dto;

import java.util.List;

public class SkillDto {
    private Long id;
    private String name;
    private List<AreaDto> areas;  // Optional: Include areas if you want nested data

    // Constructors
    public SkillDto() {}

    public SkillDto(Long id, String name, List<AreaDto> areas) {
        this.id = id;
        this.name = name;
        this.areas = areas;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
