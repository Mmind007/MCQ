package com.apexon.mcq.service;

import com.apexon.mcq.dto.QuestionDto;
import com.apexon.mcq.dto.TestResultDto;
import com.apexon.mcq.dto.UserAnswerDto;
import com.apexon.mcq.entity.Option;
import com.apexon.mcq.entity.Question;
import com.apexon.mcq.repository.OptionsRepository;
import com.apexon.mcq.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MCQService {

    private final QuestionRepository questionRepository;
    private final OptionsRepository optionRepository;



    @Autowired
    public MCQService(QuestionRepository questionRepository, OptionsRepository optionRepository) {
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
    }


    public List<QuestionDto> getQuestionsByArea(Long areaId) {
        List<Question> questions = questionRepository.findByAreaId(areaId);
        return questions.stream().map(this::mapToDto).collect(Collectors.toList());
    }


    /*public TestResultDto evaluateTest(Long areaId, List<UserAnswerDto> userAnswers) {
        int correctCount = 0;

        for (UserAnswerDto answer : userAnswers) {
            Question question = repository.findById(answer.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            if (question.getCorrectOption().equalsIgnoreCase(answer.getSelectedOption())) {
                correctCount++;
            }
        }

        int total = userAnswers.size();
        int score = (int) ((correctCount * 100.0) / total);

        return new TestResultDto(total, correctCount, score);
    }*/

    public TestResultDto evaluateTest(Long areaId, List<UserAnswerDto> userAnswers) {
        int total = userAnswers.size();
        int correct = 0;
        for (UserAnswerDto answer : userAnswers) {
            Question question = questionRepository.findById(answer.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            List<Option> options = optionRepository.findByQuestion(question);

            int index = switch (answer.getSelectedOption()) {
                case "A" -> 0;
                case "B" -> 1;
                case "C" -> 2;
                case "D" -> 3;
                default -> throw new RuntimeException("Invalid option label: " + answer.getSelectedOption());
            };

            if (index >= options.size()) {
                throw new RuntimeException("Selected option index out of range");
            }

            Option selected = options.get(index);

            if (selected.isCorrect()) {
                correct++;
            }
        }
        int score = (int) ((correct * 100.0) / total);
        return new TestResultDto(total, correct, score);
    }






    private QuestionDto mapToDto (Question q){
        return new QuestionDto(
                q.getSkill().getSkillId(),
                q.getQuestionStatement(),
                q.getExplanation(),
                q.getOptions() // using entity Option directly
        );
    }

}
