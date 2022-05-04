package com.example.Quiz.service;

import com.example.Quiz.entity.User;

import java.util.List;

public interface UserService{

    User addUser(String username, Long result);
    List<User> getUserResults();
}
