package com.example.Quiz.controller;

import com.example.Quiz.entity.Questions;
import com.example.Quiz.service.QuestionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.example.Quiz.constant.FileConstant.*;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RequestMapping("/question")
@RestController
@AllArgsConstructor
@Slf4j
public class QuestionsController {

    private final QuestionService questionService;

    @GetMapping("/{numberQuestion}")
    public Questions addUser(@PathVariable("numberQuestion") String numberQuestion){
        Long numberIdQuestion = Long.parseLong(numberQuestion);
        if (numberIdQuestion == 0){
            questionService.createQuestions(QUESTIONS);
        }
       return questionService.getQuestion(Long.parseLong(numberQuestion));
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + FORWARD_SLASH + fileName));
    }
}
