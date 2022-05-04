package com.example.Quiz.service.impl;

import com.example.Quiz.entity.Questions;
import com.example.Quiz.repository.QuestionsRepository;
import com.example.Quiz.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.example.Quiz.constant.FileConstant.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


@Service
@AllArgsConstructor
public class QuestionsServiceImpl implements QuestionService {

    private final QuestionsRepository questionsRepository;

    @Override
    public void createQuestions(String[][] questions) {
        for (String[] questionAndAnswers: questions) {
            Questions newQuestion = new Questions(Long.parseLong(questionAndAnswers[0]), questionAndAnswers[1], questionAndAnswers[2], questionAndAnswers[3], questionAndAnswers[4], questionAndAnswers[5], questionAndAnswers[6], null);
            newQuestion.setImageUrl(setImageUrl(questionAndAnswers[0]));
            questionsRepository.save(newQuestion);
        }
    }

    @Override
    public Questions getQuestion(Long id) {
        return questionsRepository.findById(id).get();
    }

    @Override
    public void saveImage(MultipartFile image, String name) throws IOException {
        saveProfileImage(image, name);
    }

    private void saveProfileImage(MultipartFile image, String name) throws IOException {
        if (image != null){
            Path userFolder = Paths.get(USER_FOLDER).toAbsolutePath().normalize();
            if (!Files.exists(userFolder)){
                Files.createDirectories(userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + name + DOT + JPG_EXTENSION));
            Files.copy(image.getInputStream(), userFolder.resolve(name + DOT + JPG_EXTENSION), REPLACE_EXISTING);
        }
    }

    private String setImageUrl(String number) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().
                path(QUESTION_IMAGE_PATH + FORWARD_SLASH + number + DOT + JPG_EXTENSION).toUriString();
    }


}
