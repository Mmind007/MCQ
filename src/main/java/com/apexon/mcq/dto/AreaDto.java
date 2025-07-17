package com.apexon.mcq.dto;

import jakarta.validation.constraints.NotBlank;

public class AreaDto {
	
	@NotBlank(message = "Area name must not be empty")
    private String name;

    // Constructors
    public AreaDto() {}

    public AreaDto(String name) {
        this.name = name;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
