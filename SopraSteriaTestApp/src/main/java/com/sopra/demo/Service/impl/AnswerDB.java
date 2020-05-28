package com.sopra.demo.Service.impl;

import com.sopra.demo.DB.AnswerRep;
import com.sopra.demo.DB.Entities.FormAnswerDB;
import com.sopra.demo.DB.Entities.FormDB;
import com.sopra.demo.DB.Entities.FormQuestions;
import com.sopra.demo.DB.FormRep;
import com.sopra.demo.DB.QuestionRep;
import com.sopra.demo.Service.AnswerService;
import com.sopra.demo.controllers.Answers.FormAnswer;
import com.sopra.demo.controllers.Answers.QuestionAnswer;
import com.sopra.demo.controllers.Form;
import com.sopra.demo.controllers.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

@Service
@Transactional
public class AnswerDB implements AnswerService {


    static Map<Long, FormAnswer> answersDB = new HashMap<>();

    @Autowired
    private FormRep frep;
    @Autowired
    private AnswerRep Arep;
    @Autowired
    private QuestionRep Qrep;


    @Override
    public void delForm(long id) {
        for(FormQuestions d:Qrep.findAll()){
            if(d.getFormId()==id){
                Qrep.delete(d);
            }
        }
        for(FormAnswerDB d:Arep.findAll()){
            if(d.getFormId()==id){
                Arep.delete(d);
            }
        }

    }

    @Override
    public List<FormAnswer> findAnswers() {

        FormAnswer FA = new FormAnswer();
        List<FormAnswer> FAList = new ArrayList<FormAnswer>();
        List<QuestionAnswer> QAList = new ArrayList<QuestionAnswer>();
        QuestionAnswer  QA = new QuestionAnswer();


            for(FormAnswerDB fadb:Arep.findAll()){
                FA.setUser(fadb.getUser());
                FA.setFormId(fadb.getFormId());
                FA.setId(fadb.getId());

                for(FormQuestions Qtmp:Qrep.findAll()) {
                        if(Qtmp.getFormId()==fadb.getFormId() && fadb.getQuestionId() == Qtmp.getQuestionId()) {
                            QA.setId(Qtmp.getId());
                            QA.setQuestionId(Qtmp.getQuestionId());
                            QA.setType(Qtmp.getQuestiontype());
                        if(QA.getType()==1) {
                            QA.setTextAnswer(fadb.getAnswer());
                            }
                            if(QA.getType()==2) {
                                QA.setRadioAnswer(parseInt(fadb.getAnswer()));
                            }
                            if(QA.getType()==3) {
                                //QA.setCheckBoxAnswer();
                            }
                            QAList.add(QA);
                        }
                    }

                FA.addAllAnswers(QAList);
                FAList.add(FA);
            }


            return FAList;

    }

    @Override
    public void saveAnswers(FormAnswer formAnswer) {

        FormQuestions formQuestions = new FormQuestions();
        FormAnswerDB formAnswerDB = new FormAnswerDB();

        for(QuestionAnswer QA:formAnswer.getAnswers()) {
            formQuestions.setFormId((int) formAnswer.getFormId());
            formAnswerDB.setFormId((int) formAnswer.getFormId());
            formAnswerDB.setUser(formAnswer.getUser());
            formQuestions.setQuestion(QA.getQuestion());
            formQuestions.setQuestionId(QA.getQuestionId());

            formQuestions.setQuestiontype(QA.getType());
            if(QA.getType()==1) {
                formAnswerDB.setAnswer(QA.getTextAnswer());
            }
            if(QA.getType()==2) {
                formAnswerDB.setAnswer(String.valueOf(QA.getRadioAnswer()));
            }
            if(QA.getType()==3) {
                //QA.setCheckBoxAnswer();
            }



            Qrep.save(formQuestions);
            Arep.save(formAnswerDB);
        }








    }
}
