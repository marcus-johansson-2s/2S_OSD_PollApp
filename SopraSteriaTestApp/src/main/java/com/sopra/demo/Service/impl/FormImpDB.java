package com.sopra.demo.Service.impl;


import com.sopra.demo.DB.CheckboxQuestionsRep;
import com.sopra.demo.DB.Entities.CheckboxQuestions;
import com.sopra.demo.DB.Entities.FormDB;
import com.sopra.demo.DB.Entities.QuestionsDB;
import com.sopra.demo.DB.FormRep;
import com.sopra.demo.DB.QuestionsDBRep;
import com.sopra.demo.Service.FormService;
import com.sopra.demo.controllers.Form;
import com.sopra.demo.controllers.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FormImpDB implements FormService {

    @Autowired
    private CheckboxQuestionsRep CBrep;
    @Autowired
    private QuestionsDBRep QFrep;
    @Autowired
    private FormRep frep;

    @Override
    public List<Form> findAll() {


        List<Form> theFormList = new ArrayList<>();
        List<FormDB> formList = frep.findAll();
        List<QuestionsDB> questionListDB = QFrep.findAll();

        for (FormDB tmpfdb : formList) {
            Form theForm = new Form();
            List<Question> questionList = new ArrayList<>();

            theForm.setFormId(tmpfdb.getFormId());
            theForm.setDescription(tmpfdb.getDescription());
            if (tmpfdb.getIsAnon() == 1) {
                theForm.setAnon(true);
            } else {
                theForm.setAnon(false);
            }
            if (tmpfdb.getIsActive() == 1) {
                theForm.setActive(true);
            } else {
                theForm.setActive(false);
            }
            for (QuestionsDB q : questionListDB) {

                if (q.getFormId() == tmpfdb.getFormId()) {
                    Question tmpQuestion = new Question();
                    tmpQuestion.setQuestion(q.getQuestion());
                    tmpQuestion.setId(q.getQuestionId());
                    tmpQuestion.setTypeQuestion(q.getQuestiontype());

                    if (tmpQuestion.getTypeQuestion() == 3) {
                        List<CheckboxQuestions> chbtmp = CBrep.findAll();
                        for (CheckboxQuestions tmp : chbtmp) {
                            if (tmp.getFormId() == theForm.getFormId() && tmp.getQuestionId() == tmpQuestion.getId()) {
                                tmpQuestion.setCheckBoxAnswer(tmp.getId(), tmp.getAlternatives());
                            }
                        }


                    }

                    questionList.add(tmpQuestion);
                }
            }

            theForm.listSetter(questionList);
            theFormList.add(theForm);
        }


        return theFormList;
    }


    @Override
    public void saveAll(List<Form> form) {

        List<QuestionsDB> questionDBlist = new ArrayList<>();
        List<FormDB> formDBlist = new ArrayList<>();


        for (Form f : form) {
            FormDB tmpForm = new FormDB();
            tmpForm.setFormId((int) f.getFormId());
            tmpForm.setIsActive(f.getActive());
            tmpForm.setIsAnon(f.getAnon());
            tmpForm.setDescription(f.getDescription());
            for (Question q : f.getQuestionList()) {
                QuestionsDB tmp = new QuestionsDB();
                tmp.setFormId(tmpForm.getFormId());
                tmp.setQuestion(q.getQuestion());
                tmp.setQuestionId(q.getId());
                tmp.setQuestiontype(q.getTypeQuestion());
                questionDBlist.add(tmp);
            }
            formDBlist.add(tmpForm);
        }

        QFrep.saveAll(questionDBlist);
        frep.saveAll(formDBlist);


    }

    @Override
    public void delForm(long id) {
        for (FormDB d : frep.findAll()) {
            if (d.getFormId() == id) {
                frep.delete(d);
            }
        }
        for (QuestionsDB d : QFrep.findAll()) {
            if (d.getFormId() == id) {
                QFrep.delete(d);
            }
        }

    }

    @Override
    public void saveForm(Form form) {
        List<QuestionsDB> questionDBlist = new ArrayList<>();

        FormDB tmpForm = new FormDB();
        tmpForm.setFormId((int) form.getFormId());
        tmpForm.setIsActive(form.getActive());
        tmpForm.setIsAnon(form.getAnon());
        tmpForm.setDescription(form.getDescription());




        frep.save(tmpForm);



    }

    @Override
    public Form findingOne(long theOne) {


        Form theForm = new Form();
        List<FormDB> formList = frep.findAll();
        List<QuestionsDB> questionListDB = QFrep.findAll();


        for (FormDB tmpfdb : formList) {
            if (tmpfdb.getFormId() == theOne) {
                List<Question> questionList = new ArrayList<>();
                theForm.setFormId(tmpfdb.getFormId());
                theForm.setDescription(tmpfdb.getDescription());
                if (tmpfdb.getIsAnon() == 1) {
                    theForm.setAnon(true);
                } else {
                    theForm.setAnon(false);
                }
                if (tmpfdb.getIsActive() == 1) {
                    theForm.setActive(true);
                } else {
                    theForm.setActive(false);
                }
                for (QuestionsDB q : questionListDB) {

                    if (q.getFormId() == tmpfdb.getFormId()) {
                        Question tmpQuestion = new Question();
                        tmpQuestion.setQuestion(q.getQuestion());
                        tmpQuestion.setId(q.getQuestionId());
                        tmpQuestion.setTypeQuestion(q.getQuestiontype());

                        if (tmpQuestion.getTypeQuestion() == 3) {
                            List<CheckboxQuestions> chbtmp = CBrep.findAll();
                            for (CheckboxQuestions tmp : chbtmp) {
                                if (tmp.getFormId() == theForm.getFormId() && tmp.getQuestionId() == tmpQuestion.getId()) {
                                    tmpQuestion.setCheckBoxAnswer(tmp.getId(), tmp.getAlternatives());
                                }
                            }


                        }


                        questionList.add(tmpQuestion);
                    }
                }

                theForm.listSetter(questionList);
            }


        }

        return theForm;
    }

    @Override
    public void UpdatingQuestion(Form DTO) {

        List<CheckboxQuestions> cbQlist = CBrep.findAll();
        List<QuestionsDB> test = QFrep.findAll();
        List<QuestionsDB> test2 = new ArrayList<>();
        List<Integer> typeList = new ArrayList<>();

        List<CheckboxQuestions> tmpCheckbox = new ArrayList<>();

        List<Integer> QidList = new ArrayList<>();
        int QID = 0;
        for (QuestionsDB d : test) {


            if (d.getFormId() == DTO.getFormId()) {
                int type = d.getQuestiontype();
                typeList.add(type);

                for (CheckboxQuestions cQ : cbQlist) {

                    if (cQ.getFormId() == DTO.getFormId() && cQ.getQuestionId() == d.getQuestionId()) {
                        cQ.setQuestionId(QID);
                        tmpCheckbox.add(cQ);
                        CBrep.delete(cQ);
                    }
                }

                for (Question q : DTO.getQuestionList()) {
                    if (d.getFormId() == DTO.getFormId()) {
                        QFrep.delete(d);


                    }

                }

                // QidList.add(d.getQuestionId());
                QID++;
            }

        }
        int counter = 0;
        int counterID = 0;
        CBrep.flush();

        List<QuestionsDB> questionDBlist = new ArrayList<>();

        for (Question q : DTO.getQuestionList()) {

            int dispersion = 0;
            QuestionsDB tmp = new QuestionsDB();
            tmp.setFormId((int) DTO.getFormId());
            tmp.setQuestion(q.getQuestion());

            tmp.setQuestionId(counterID);
            // tmp.setQuestionId(QidList.get(counter));


            tmp.setQuestiontype(typeList.get(counter));

            if (tmp.getQuestiontype() != 3) {
                dispersion++;

            }

            if (tmp.getQuestiontype() == 3) {

                for (CheckboxQuestions cq : tmpCheckbox) {

                    if (cq.getQuestionId() + dispersion == tmp.getQuestionId()) {

                        CBrep.save(cq);
                        dispersion=0;

                    }

                }


            }
            questionDBlist.add(tmp);
            counter++;
            counterID++;


        }

        QFrep.saveAll(questionDBlist);


    }

    @Override
    public void deletingQuestion(long question, long formId) {

        List<CheckboxQuestions> cbQlist = CBrep.findAll();
        List<QuestionsDB> qdbList = QFrep.findAll();

        for (CheckboxQuestions cQ : cbQlist) {
            if (cQ.getFormId() == formId && cQ.getQuestionId() == question) {
                CBrep.delete(cQ);
            }

        }
        for (QuestionsDB qdb : qdbList) {
            if (qdb.getFormId() == formId && qdb.getQuestionId() == question) {


                QFrep.delete(qdb);

            }
        }
    }

    @Override
    public String getQuestion(int formId, int questionID) {
        String match = "";

        List<QuestionsDB> listCheck = QFrep.findAll();

        for (QuestionsDB qb : listCheck) {
            if (qb.getQuestionId() == questionID && qb.getFormId() == formId) {
                match = qb.getQuestion();
                return match;
            }

        }


        return match;
    }


    @Override
    public void savingQuestion(Question q, long formId) {

        QuestionsDB tmpQ = new QuestionsDB();

        tmpQ.setFormId((int) formId);
        tmpQ.setQuestion(q.getQuestion());
        tmpQ.setQuestionId(q.getId());
        tmpQ.setQuestiontype(q.getTypeQuestion());

        if (q.getTypeQuestion() == 3) {
            for (Map.Entry<Integer, String> entry : q.getCheckBoxAnswer().entrySet()) {
                CheckboxQuestions tmp = new CheckboxQuestions();
                tmp.setFormId((int) formId);
                tmp.setQuestionId(q.getId());
                tmp.setAlternatives(entry.getValue());
                CBrep.save(tmp);

            }


        }


        QFrep.save(tmpQ);


    }

    @Override
    public void activate(long formId) {

        for (FormDB f : frep.findAll()) {
            if (f.getFormId() == formId) {
                f.setIsActive(true);
            }

        }
    }

    @Override
    public boolean existsDoubles(long id) {


        int tmpInt = 1;
        List<FormDB> tmpFormDB = frep.findAll();

/*
        for(FormDB tmp:tmpFormDB) {
            if(tmp.getFormId()==tmpInt){
               tmpInt++;
            }}




  for(int i=0;i<frep.findAll().size();i++){
            if(frep.findAll().get(i).getFormId()==tmpInt) {
                tmpInt++;
            }
        }
        /*

 */

        for (FormDB tmp : tmpFormDB) {
            if (tmp.getFormId() == (int) id) {
                return true;
            }
        }


        return false;
    }

    @Override
    public boolean existsDoublesQuestions(long id, int doppler) {

        List<QuestionsDB> tmpQuestionDB = QFrep.findAll();

        for (QuestionsDB tmp : tmpQuestionDB) {
            if (tmp.getFormId() == id && tmp.getQuestionId() == doppler) {
                return true;
            }

        }

        return false;
    }
}

