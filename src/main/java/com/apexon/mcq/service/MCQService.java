package com.apexon.mcq.service;

import com.apexon.mcq.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class MCQService {
    private QuestionRepository repository;

    public MCQService(QuestionRepository repository) {
        this.repository = repository;
    }


}
