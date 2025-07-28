package com.apexon.mcq.controller;
import com.apexon.mcq.dto.QuestionDto;
import com.apexon.mcq.dto.TestResultDto;
import com.apexon.mcq.dto.UserAnswerDto;
import com.apexon.mcq.service.MCQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/skillmgmt/skills/{skillId}/areas")
public class MCQController {
    @Autowired
    private MCQService mcqService;

    @GetMapping("/{areaId}/questions")
    public ResponseEntity<List<QuestionDto>> getQuestions(@PathVariable Long areaId) {
        List<QuestionDto> questions = mcqService.getQuestionsByArea(areaId);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/{areaId}/submit")
    public ResponseEntity<TestResultDto> submitTest(
            @PathVariable("skillId") Long skillId,
            @PathVariable("areaId") Long areaId,
            @RequestBody List<UserAnswerDto> userAnswers) {

        TestResultDto result = mcqService.evaluateTest(areaId, userAnswers);
        return ResponseEntity.ok(result);
    }

}
