package com.apexon.mcq.dto;

import com.apexon.mcq.entity.LearningMaterial;
import com.apexon.mcq.entity.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LearningProgressResponseDto {

    private User user; //call the user service
    private LearningMaterial learningMaterial;
    private String summary;
    private LocalDate localDate;
    private Long progressPercentage;

}
