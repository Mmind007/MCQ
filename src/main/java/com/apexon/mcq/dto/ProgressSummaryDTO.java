package com.apexon.mcq.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProgressSummaryDTO {

    @NotNull(message = "Learning material ID is required")
    private Long learningMaterialId;


    @Min(value = 0, message = "Progress cannot be less than 0")
    @Max(value = 100, message = "Progress cannot be more than 100")
    @NotNull(message = "Learning material ID is required")
    private Integer maxProgress;

    @NotNull
    private String summary;
}
