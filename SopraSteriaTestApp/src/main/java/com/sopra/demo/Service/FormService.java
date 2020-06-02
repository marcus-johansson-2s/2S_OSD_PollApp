package com.sopra.demo.Service;

import com.sopra.demo.DB.Entities.FormDB;
import com.sopra.demo.controllers.Form;
import com.sopra.demo.controllers.Question;

import java.util.List;

public interface FormService {

    List<Form> findAll();
    void saveAll(List<Form> form);
    void delForm(long id);
    public void saveForm(Form form);
    public Form findingOne(long theOne);
    public boolean existsDoubles(long test);
    public boolean existsDoublesQuestions(long id,int doppler);
    public void savingQuestion(Question q,long formId);
    public void activate(long formId);
    public void deletingQuestion(long question,long formId);
    public void UpdatingQuestion(Form DTO);
    public String getQuestion(int formId,int questionID);



}
