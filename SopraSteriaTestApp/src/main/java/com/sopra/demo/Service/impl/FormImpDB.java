package com.sopra.demo.Service.impl;



import com.sopra.demo.DB.AnswerRep;
import com.sopra.demo.DB.FormRep;
import com.sopra.demo.DB.QuestionRep;
import com.sopra.demo.Service.AnswerService;
import com.sopra.demo.Service.FormService;
import com.sopra.demo.controllers.Answers.FormAnswer;
import com.sopra.demo.controllers.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class FormImpDB implements FormService {


    @Autowired
    private FormRep frep;
    @Autowired
    private AnswerRep Arep;
    @Autowired
    private QuestionRep Qrep;


    @Override
    public List<Form> findAll() {
        return null;
    }

    @Override
    public void saveAll(List<Form> form) {

    }

    @Override
    public void delForm(long id) {
        frep.deleteById((int)id);
    }

    @Override
    public void save(Form form) {

    }

    @Override
    public Form findingOne(long theOne) {
        return null;
    }

    @Override
    public boolean existsDoubles(long id) {
        return false;
    }

    @Override
    public boolean existsDoublesQuestions(long id, int doppler) {
        return false;
    }
}

