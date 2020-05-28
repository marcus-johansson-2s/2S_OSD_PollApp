package com.sopra.demo.Service;

import com.sopra.demo.controllers.Answers.FormAnswer;

import java.util.List;

public interface AnswerService {

    public void delForm(long id);
    List<FormAnswer> findAnswers();
    void saveAnswers(FormAnswer formAnswer);
}
