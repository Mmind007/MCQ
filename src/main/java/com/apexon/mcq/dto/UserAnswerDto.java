package com.apexon.mcq.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerDto {
    private Long questionId;
    private String selectedOption; // "A", "B", "C", "D"
}
