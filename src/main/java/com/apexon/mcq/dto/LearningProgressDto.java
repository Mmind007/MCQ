package com.apexon.mcq.dto;

import com.apexon.mcq.entity.LearningMaterial;
import com.apexon.mcq.entity.User;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class LearningProgressDto {

    private Long learningProgressId;
    private LearningMaterial learningMaterial;
    private String summary;
    private LocalDateTime localDate;
    private Long progressPercentage;


}
