package com.example.Quiz.service.impl;

import com.example.Quiz.entity.User;
import com.example.Quiz.repository.UserRepository;
import com.example.Quiz.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User addUser(String username, Long result) {
        User user = new User(username, result);
        return userRepository.save(user);
    }

    @Override
    public List<User> getUserResults(){
        return userRepository.findAll();
    }
}
