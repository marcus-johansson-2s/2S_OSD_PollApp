package com.sopra.demo.controllers.Service;

import com.sopra.demo.controllers.Answers.FormAnswer;
import com.sopra.demo.controllers.Form;

import java.util.List;

public interface FormService {

    List<Form> findAll();
    void saveAll(List<Form> form);
    void delForm(long id);
    public void save(Form form);
    public Form findingOne(long theOne);
    public boolean existsDoubles(long id);
    public boolean existsDoublesQuestions(long id,int doppler);




}
