package com.sopra.demo.controllers.Service;

import com.sopra.demo.controllers.Answers.FormAnswer;
import com.sopra.demo.controllers.Form;

import java.util.List;

public interface FormService {

    List<Form> findAll();
    void saveAll(List<Form> form);






}