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
import org.apache.commons.codec.binary.StringUtils;
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


        Map<String,QuestionAnswer> questionAnswerListMap =  new HashMap<>();
        Map<String,FormAnswer> formAnswerListMap =  new HashMap<>();
        List<FormAnswer> FAList = new ArrayList<FormAnswer>();
        List<FormAnswerDB> fadbList = Arep.findAll();
        List<FormQuestions> qDBList = Qrep.findAll();




     for(FormAnswerDB faDB:fadbList) {
         FormAnswer FA = new FormAnswer();

         FA.setUser(faDB.getUser());
         FA.setFormId(faDB.getFormId());
         FA.setId(faDB.getId());

         for(FormQuestions fqDB:qDBList) {
                    if(faDB.getFormId()==fqDB.getFormId() && faDB.getQuestionId() ==fqDB.getQuestionId()){
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
                        }

                        //QAList.add(QA);
                        questionAnswerListMap.put(faDB.getUser()+"("+faDB.getFormId()+")"+fqDB.getQuestionId(),QA);
                    }
            }


         formAnswerListMap.put(FA.getUser()+"("+FA.getFormId()+")",FA);

     }

        //String x = "test string (67)".replaceAll(".*\\(|\\).*", "");
/*
        for(Map.Entry<String, FormAnswer> form : formAnswerListMap.entrySet()) {
            System.out.println("-------------HASHMAP--------------");
            System.out.println(form.getValue().getFormId());
            for (Map.Entry<String, QuestionAnswer> question : questionAnswerListMap.entrySet()) {
                System.out.println(question.getValue().getQuestion());
            }
        }


 */
     for(Map.Entry<String, FormAnswer> form : formAnswerListMap.entrySet()){

         System.out.println("Form id INPUt = "+form.getValue().getFormId());
         List<QuestionAnswer> QAList = new ArrayList<QuestionAnswer>();
         for (Map.Entry<String, QuestionAnswer> question : questionAnswerListMap.entrySet()) {
             String newKey = form.getKey()+question.getValue().getQuestionId();
            if(newKey.equals(question.getKey())){
                QAList.add(question.getValue());
            }
         }


        form.getValue().addAllAnswers(QAList);
         FAList.add(form.getValue());



         }






/*
        for (FormAnswerDB fadb : fadbList) {

            if(FAList.isEmpty()) {
                FAList.add(FA);
            }
            else{
                if(!FAList.get(p).getUser().equals(fadb.getUser())){
                    System.out.println(FAList.get(p).getUser());
                    System.out.println(fadb.getUser());
                    FAList.add(FA);
                    p++;
                    QAList.clear();
                }

            }

            FAList.get(p).setUser(fadb.getUser());
            FAList.get(p).setFormId(fadb.getFormId());
            FAList.get(p).setId(fadb.getId());


            for (FormQuestions fq : qDBList) {

                if (fadb.getFormId() == fq.getFormId() && fq.getQuestionId() == fadb.getQuestionId()) {
                    QuestionAnswer QA = new QuestionAnswer();
                    QA.setId(Qrep.findAll().size());
                    QA.setType(fq.getQuestiontype());
                    QA.setQuestion(fq.getQuestion());
                    QA.setQuestionId(fadb.getQuestionId());
                    // qa.setId(form.questionList.size());


                    if (QA.getType() == 1) {
                        QA.setTextAnswer(fadb.getAnswer());
                    }
                    if (QA.getType() == 2) {
                        QA.setRadioAnswer(parseInt(fadb.getAnswer()));
                    }
                    if (QA.getType() == 3) {
                        //QA.setCheckBoxAnswer();
                    }
                   // FA.addAnswers(QA);
                    QAList.add(QA);
                }


            }

            FAList.get(p).addAllAnswers(QAList);
            FAList.set(p,FA);


        }


        for(FormAnswer fa :FAList){
                System.out.println("----------------------------------------");
                System.out.println(fa.getFormId());
                for(int i=0;i<fa.getAnswers().size();i++) {
                    System.out.println(fa.getAnswers().get(i).getQuestion());
                    System.out.println(fa.getAnswers().get(i).getTextAnswer());
                }
                System.out.println("----------------------------------------");
                }
 */

            return FAList;

    }

    @Override
    public void saveAnswers(FormAnswer formAnswer) {



List<FormAnswerDB> aList = new ArrayList<>();
        List<FormQuestions> fList = new ArrayList<>();

        for(QuestionAnswer QA:formAnswer.getAnswers()) {



            FormQuestions formQuestions = new FormQuestions();
            FormAnswerDB formAnswerDB = new FormAnswerDB();

            formQuestions.setFormId((int) formAnswer.getFormId());
            formAnswerDB.setFormId((int) formAnswer.getFormId());
            formAnswerDB.setUser(formAnswer.getUser());
            formQuestions.setQuestion(QA.getQuestion());
            formQuestions.setQuestionId(QA.getQuestionId());
            formAnswerDB.setQuestionId(QA.getQuestionId());



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

        fList.add(formQuestions);
        aList.add(formAnswerDB);
        }



       // Qrep.save(formQuestions);
        //Arep.save(formAnswerDB);
        Qrep.saveAll(fList);
        Arep.saveAll(aList);

    }
}
