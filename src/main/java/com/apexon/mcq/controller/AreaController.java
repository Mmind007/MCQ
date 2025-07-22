package com.apexon.mcq.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apexon.mcq.dto.AreaDto;
import com.apexon.mcq.service.AreaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/skillmgmt/areas")
public class AreaController {

	@Autowired
	private AreaService areaService;

	@PostMapping()
	public ResponseEntity<AreaDto> createArea(@RequestParam Long skillId, @Valid @RequestBody AreaDto areaDto) {
		AreaDto createdArea = areaService.createArea(skillId, areaDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdArea);
	}

	@PostMapping("/batch")
	public ResponseEntity<List<AreaDto>> createAreas(@RequestParam Long skillId, @RequestBody List<AreaDto> areas) {
		List<AreaDto> createdAreas = areas.stream().map(areaDto -> areaService.createArea(skillId, areaDto))
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.CREATED).body(createdAreas);
	}

	@GetMapping()
	public ResponseEntity<List<AreaDto>> getAreasBySkill(@RequestParam Long skillId) {
		return areaService.getAreasBySkill(skillId);
	}

	@GetMapping("/{areaId}")
	public ResponseEntity<AreaDto> getAreaByAreaId(@PathVariable Long areaId) {
		return areaService.getAreaByAreaId(areaId);
	}
	
	@PutMapping("/{areaId}")
	public ResponseEntity<AreaDto> updateArea(@PathVariable long areaId, @Valid @RequestBody AreaDto areaDto) {
		AreaDto updated = areaService.updateArea(areaId, areaDto);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{areaId}")
	public ResponseEntity<Void> deleteArea(@PathVariable long areaId) {
		areaService.deleteArea(areaId);
		return ResponseEntity.noContent().build();
	}

}