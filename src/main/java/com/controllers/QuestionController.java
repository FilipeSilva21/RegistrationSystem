package com.controllers;

import com.services.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/question")
public class QuestionController {

    @Autowired
    private QuestionsService questionsService;
}
