package com.sopra.demo.Service.impl;

import com.sopra.demo.DB.AnswerRep;
import com.sopra.demo.DB.CheckboxAnswerRep;
import com.sopra.demo.DB.CheckboxQuestionsRep;
import com.sopra.demo.DB.Entities.CheckboxAnswer;
import com.sopra.demo.DB.Entities.CheckboxQuestions;
import com.sopra.demo.DB.Entities.FormAnswerDB;
import com.sopra.demo.DB.Entities.FormQuestions;
import com.sopra.demo.DB.QuestionRep;
import com.sopra.demo.Service.AnswerService;
import com.sopra.demo.controllers.Answers.FormAnswer;
import com.sopra.demo.controllers.Answers.QuestionAnswer;
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
    @Autowired
    private CheckboxQuestionsRep CBquestions;
    @Autowired
    private CheckboxAnswerRep CBanswer;
    @Autowired
    private AnswerRep Arep;
    @Autowired
    private QuestionRep Qrep;


    @Override
    public void delForm(long id) {

        List<CheckboxAnswer> cList = CBanswer.findAll();
        List<CheckboxQuestions> cqList = CBquestions.findAll();

        for (FormQuestions d : Qrep.findAll()) {
            if (d.getFormId() == id) {
                Qrep.delete(d);
            }
        }
        for (CheckboxQuestions tmp : cqList) {
            if (tmp.getFormId() == id) {
                CBquestions.delete(tmp);
                for (CheckboxAnswer ca : cList) {
                    if (ca.getIdanswer() == tmp.getId()) {
                        CBanswer.delete(ca);
                    }
                }
            }
        }
        for (FormAnswerDB d : Arep.findAll()) {
            if (d.getFormId() == id) {
                Arep.delete(d);
            }
        }

    }

    @Override
    public List<FormAnswer> findAnswers() {


        Map<String, QuestionAnswer> questionAnswerListMap = new HashMap<>();
        Map<String, FormAnswer> formAnswerListMap = new HashMap<>();
        List<FormAnswer> FAList = new ArrayList<FormAnswer>();
        List<FormAnswerDB> fadbList = Arep.findAll();
        List<FormQuestions> qDBList = Qrep.findAll();


        for (FormAnswerDB faDB : fadbList) {
            FormAnswer FA = new FormAnswer();

            FA.setUser(faDB.getUser());
            FA.setFormId(faDB.getFormId());
            FA.setId(faDB.getId());

            for (FormQuestions fqDB : qDBList) {
                if (faDB.getFormId() == fqDB.getFormId() && faDB.getQuestionId() == fqDB.getQuestionId()) {
                    QuestionAnswer QA = new QuestionAnswer();
                    QA.setId(Qrep.findAll().size());
                    QA.setType(fqDB.getQuestiontype());
                    QA.setQuestion(fqDB.getQuestion());
                    QA.setQuestionId(faDB.getQuestionId());
                    // qa.setId(form.questionList.size());
                    if (QA.getType() == 1) {
                        QA.setTextAnswer(faDB.getAnswer());
                    }
                    if (QA.getType() == 2) {
                        QA.setRadioAnswer(parseInt(faDB.getAnswer()));
                    }
                    if (QA.getType() == 3) {
                        //QA.setCheckBoxAnswer();
                        List<CheckboxAnswer> CAList = CBanswer.findAll();
                        List<CheckboxQuestions> CQList = CBquestions.findAll();
                        List<Integer> tmpList = new ArrayList<>();

                        for (CheckboxAnswer ca : CAList) {
                            if (FA.getUser().equals(ca.getUser())) {
                                if (CBquestions.findById(ca.getIdanswer()).get().getQuestionId() == QA.getQuestionId() && CBquestions.findById(ca.getIdanswer()).get().getFormId() == FA.getFormId())

                                    tmpList.add(ca.getIdanswer());

                            }


                        }
                        QA.setCheckBoxAnswer(tmpList);


                    }

                    //QAList.add(QA);
                    questionAnswerListMap.put(faDB.getUser() + "(" + faDB.getFormId() + ")" + fqDB.getQuestionId(), QA);
                }
            }


            formAnswerListMap.put(FA.getUser() + "(" + FA.getFormId() + ")", FA);

        }


        for (Map.Entry<String, FormAnswer> form : formAnswerListMap.entrySet()) {

            // System.out.println("Form id INPUt = "+form.getValue().getFormId());
            List<QuestionAnswer> QAList = new ArrayList<QuestionAnswer>();
            for (Map.Entry<String, QuestionAnswer> question : questionAnswerListMap.entrySet()) {
                String newKey = form.getKey() + question.getValue().getQuestionId();
                if (newKey.equals(question.getKey())) {
                    QAList.add(question.getValue());
                }
            }


            form.getValue().addAllAnswers(QAList);
            FAList.add(form.getValue());


        }




        return FAList;

    }

    @Override
    public void saveAnswers(FormAnswer formAnswer) {


        List<FormAnswerDB> aList = new ArrayList<>();
        List<FormQuestions> fList = new ArrayList<>();

        for (QuestionAnswer QA : formAnswer.getAnswers()) {


            FormQuestions formQuestions = new FormQuestions();
            FormAnswerDB formAnswerDB = new FormAnswerDB();

            formQuestions.setFormId((int) formAnswer.getFormId());
            formAnswerDB.setFormId((int) formAnswer.getFormId());
            formAnswerDB.setUser(formAnswer.getUser());
            formQuestions.setQuestion(QA.getQuestion());
            formQuestions.setQuestionId(QA.getQuestionId());
            formAnswerDB.setQuestionId(QA.getQuestionId());


            formQuestions.setQuestiontype(QA.getType());
            if (QA.getType() == 1) {
                formAnswerDB.setAnswer(QA.getTextAnswer());
            }
            if (QA.getType() == 2) {
                formAnswerDB.setAnswer(String.valueOf(QA.getRadioAnswer()));
            }
            if (QA.getType() == 3) {

                List<CheckboxQuestions> cbList = CBquestions.findAll();

                for (int fa : QA.getCheckBoxAnswer()) {
                    CheckboxAnswer cbTmpAnswer = new CheckboxAnswer();

                    cbTmpAnswer.setIdanswer(fa);
                    cbTmpAnswer.setUser(formAnswer.getUser());
                    CBanswer.save(cbTmpAnswer);


                }





            }

            fList.add(formQuestions);
            aList.add(formAnswerDB);
        }


        // Qrep.save(formQuestions);
        //Arep.save(formAnswerDB);
        Qrep.saveAll(fList);
        Arep.saveAll(aList);

    }
}
