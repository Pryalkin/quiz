package com.example.Quiz.controller;

import com.example.Quiz.entity.Questions;
import com.example.Quiz.service.QuestionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.example.Quiz.constant.FileConstant.FORWARD_SLASH;
import static com.example.Quiz.constant.FileConstant.USER_FOLDER;
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
            String questions [][]= {{"0", "В какой стране придумали шаурму?", "В России", "В Германии", "В Лошице", "В Китае", "2"},
                    {"1", "Кто написал книгу 'Война и мир'?", "Достоевский", "Сладкий", "Толстой", "Горький", "3"},
                    {"2", "Что изображено на картинке?", "Цветочки", "Статуя свободы", "Бурдж-халифа", "Эйфелева башня", "4"},
                    {"3", "Кто изображен на картинке?", "Киану Ривз", "Сильвестр Сталлоне", "Джонни Депп", "Пол Маккарти", "1"},
                    {"4", "Что на картинке?", "Эспрессо", "Нескафе растворимый", "Каппучино", "Фредо", "3"},
                    {"5", "Откуда родом мороженное?", "Из Китая", "Из Англии", "Из Бразилии", "Из Италии", "1"},
                    {"6", "Что изображено на картинке?", "Омлет", "Борщ", "Щи", "Запеканка", "2"}};
            questionService.createQuestions(questions);
        }
       return questionService.getQuestion(Long.parseLong(numberQuestion));
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + FORWARD_SLASH + fileName));
    }
}
