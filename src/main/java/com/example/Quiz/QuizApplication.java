package com.example.Quiz;

import com.example.Quiz.repository.QuestionsRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizApplication {


	private QuestionsRepository questionsRepository;

	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args);
	}

}
