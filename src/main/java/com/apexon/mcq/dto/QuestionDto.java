package com.apexon.mcq.dto;

import com.apexon.mcq.entity.Option;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "Question", "questionStatement", "options","explanation" })
public class QuestionDto {
    @JsonProperty("Question")
    private int questionNumber;
    private String questionStatement;
    private Map<String, String> options;
    private String explanation;
}
