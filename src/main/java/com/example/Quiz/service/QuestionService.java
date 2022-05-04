package com.example.Quiz.service;

import com.example.Quiz.entity.Questions;

public interface QuestionService {

    void createQuestions(String[][] questions);
    Questions getQuestion(Long id);
}

