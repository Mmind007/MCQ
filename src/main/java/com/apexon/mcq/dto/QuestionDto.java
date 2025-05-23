package com.apexon.mcq.dto;

import com.apexon.mcq.entity.Option;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private long skillId;
    private String questionStatement;
    private String explanation;
    private List<Option> options;

}
