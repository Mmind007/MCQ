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

    @NotBlank
    private long learningMaterial;

    @Column(columnDefinition = "TEXT")
    @Size(max = 5000, message = "Summary can't exceed 5000 characters")
    private String summary;

    @Min(value = 0)
    @Max(value = 100)
    private long progressPercentage;

    /*@Column(columnDefinition = "TEXT")
    @Size(max = 5000, message = "Summary can't exceed 5000 characters")
    private String nextPlan;*/



    /*@NotNull(message = "Area is required")
    @ManyToOne(optional = false) // NR if id
    //call the service
    private Area area; //may not be in package, prefer adding id*/


        //auditing fields



    /*@NotNull(message = "Progress percentage is required")
    @Min(value = 0, message = "Progress cannot be less than 0")
    @Max(value = 100, message = "Progress cannot exceed 100")
    @Column(name = "progress_percentage", nullable = false)
    private Integer progressPercentage;*/


        //25 50 75


}
