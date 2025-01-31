package com.services;

import com.DTOs.CreateQuestionDTO;
import com.DTOs.UpdateQuestionDTO;
import com.DTOs.UpdateUserDTO;
import com.Repositories.QuestionRepository;
import com.models.Question;
import com.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionsService {

    @Autowired
    private QuestionRepository repository;

    public Long createQuestion (CreateQuestionDTO createQuestionDTO){

        var question = new Question(
                null,
                createQuestionDTO.question()
        );

        var questionSaved = repository.save(question);

        return questionSaved.getQuestionId();
    }

    public List<Question> getAllQuestions(){

        return repository.findAll();
    }

    public Optional<Question> getQuestionById (Long questionId){

        return repository.findById(questionId);
    }

    public void updateUser(Long questionId, UpdateQuestionDTO updateQuestionDTO) throws Exception {

        var questionExists = repository.findById(questionId);

        if (questionExists.isPresent()) {
            var question = questionExists.get();

            if (updateQuestionDTO.question() != null) {
                question.setQuestion(updateQuestionDTO.question());
            }
            repository.save(question);
            System.out.println("Questão atualizada com sucesso");
        } else {
            throw new Exception("Questão nao existe");
        }
    }

    public void deleteQuestion(Long questionId) {
        if (questionId <= 4) {
            throw new IllegalArgumentException("As 4 primeiras perguntas não podem ser apagadas.");
        }
        repository.deleteById(questionId);
    }
}
