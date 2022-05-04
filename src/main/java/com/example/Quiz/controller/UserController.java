package com.example.Quiz.controller;

import com.example.Quiz.entity.User;
import com.example.Quiz.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/authorization")
@RestController
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/username")
    public String addUser(@RequestParam("username") String username,
                          @RequestParam("result") String result){
       userService.addUser(username, Long.parseLong(result));
       return username;
    }

    @GetMapping("/userResults")
    public ResponseEntity<List<User>> getUserResults(){
        return new ResponseEntity<>(userService.getUserResults(), HttpStatus.OK);
    }

}
