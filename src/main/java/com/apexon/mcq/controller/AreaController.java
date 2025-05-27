package com.apexon.mcq.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apexon.mcq.dto.AreaDto;
import com.apexon.mcq.entity.Area;
import com.apexon.mcq.service.AreaService;

@RestController
@RequestMapping("/skillmgmt/skills/{skillId}/areas")
public class AreaController {
	
	@Autowired
    private AreaService areaService;

    @PostMapping
    public ResponseEntity<AreaDto> createArea(@PathVariable Long skillId, @RequestBody Area area) {
        AreaDto createdArea = areaService.createArea(skillId, area);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArea);
    }
    
    @PostMapping("/batch")
    public ResponseEntity<List<AreaDto>> createAreas(
            @PathVariable Long skillId,
            @RequestBody List<Area> areas) {
        List<AreaDto> createdAreas = areas.stream()
            .map(area -> areaService.createArea(skillId, area))
            .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdAreas);
    }

    @GetMapping
    public List<AreaDto> getAreasBySkill(@PathVariable Long skillId) {
        return areaService.getAreasBySkill(skillId);
    }
}
