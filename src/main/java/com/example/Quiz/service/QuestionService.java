package com.example.Quiz.service;

import com.example.Quiz.entity.Questions;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface QuestionService {

    void createQuestions(String[][] questions);
    Questions getQuestion(Long id);
    void saveImage(MultipartFile image, String name) throws IOException;
}

