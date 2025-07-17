package com.apexon.mcq.dto;

import com.apexon.mcq.entity.LearningMaterial;
import com.apexon.mcq.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LearningProgressRequestDto {


    @NotNull(message = "User is required")
    private User user; //call the user service

    @NotNull
    private long learningMaterial;

    @NotNull
    private Long area;

    @Column(columnDefinition = "TEXT")
    @Size(max = 5000, message = "Summary can't exceed 5000 characters")
    private String summary;

    @Min(value = 0)
    @Max(value = 100)
    private long progressPercentage;

}
