package com.controllers;

import com.DTOs.CreateQuestionDTO;
import com.DTOs.CreateUserDTO;
import com.models.Question;
import com.models.User;
import com.services.QuestionService;
import com.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @PostMapping("/users")
    public ResponseEntity<Long> createUser(@RequestBody CreateUserDTO createUserDTO){

        var userId = userService.createUser(createUserDTO);

        return ResponseEntity.created(URI.create("/v1/users/" + userId)).build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){

        var users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @PostMapping("/questions")
    public ResponseEntity<Void> createQuestion(@RequestBody CreateQuestionDTO createQuestionDTO) {
        Long questionId = questionService.createQuestion(createQuestionDTO);

        return ResponseEntity.created(URI.create("/v1/questions/" + questionId)).build();
    }

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> listQuestions() {

        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<Void> deleteQuestions(@PathVariable Long questionId) {
        if (questionService.getQuestionById(questionId).isEmpty()) {
            throw new EntityNotFoundException("Pergunta não encontrada!");
        }

        questionService.deleteQuestion(questionId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUser(@RequestParam String search){

        var users = userService.searchUser(search);

        return ResponseEntity.ok(users);
    }
}
