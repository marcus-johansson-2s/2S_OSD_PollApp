package com.sopra.demo.controllers.Service;

import com.sopra.demo.controllers.Answers.FormAnswer;
import com.sopra.demo.controllers.Form;

import java.util.List;

public interface AnswerService {

    public void delForm(long id);
    List<FormAnswer> findAnswers();
    void saveAnswers(FormAnswer formAnswer);
}
