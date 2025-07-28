package com.apexon.mcq.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResultDto {
    private int total;
    private int correct;
    private int score;
}
