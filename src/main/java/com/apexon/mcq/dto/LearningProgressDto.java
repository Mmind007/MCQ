package com.apexon.mcq.dto;

import com.apexon.mcq.entity.LearningMaterial;
import com.apexon.mcq.entity.User;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LearningProgressDto {

    private Long learningProgressId;
    @NotNull(message = "User is required")
    private User user; //call the user service
    @NotNull(message = "Learning material is required")
    private LearningMaterial learningMaterial;
    @NotBlank(message = "Summary must not be blank")
    private String summary;
    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDateTime localDate;
    @NotNull(message = "Progress percentage is required")
    @Min(value = 0, message = "Progress cannot be less than 0")
    @Max(value = 100, message = "Progress cannot be more than 100")
    private Long progressPercentage;


}
