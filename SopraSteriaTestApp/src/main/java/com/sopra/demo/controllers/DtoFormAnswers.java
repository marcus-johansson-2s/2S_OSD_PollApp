package com.sopra.demo.controllers;

import com.sopra.demo.controllers.Answers.FormAnswer;
import com.sopra.demo.controllers.Answers.QuestionAnswer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DtoFormAnswers {

    private Form form = new Form();
    private List<FormAnswer> formAnswer = new ArrayList<>();

    public DtoFormAnswers() {
    }


    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public List<FormAnswer> getFormAnswer() {
        return formAnswer;
    }

    public void addFormAnswer(FormAnswer formAnswer) {
        this.formAnswer.add(formAnswer);
    }


    ////////////////converts this object to FINAL dto for printing purposes in html
    public FinalDTO functionUltra4000() {

        FinalDTO fdto = new FinalDTO();

        StringBuilder checkBox = new StringBuilder();
        StringBuilder outPutBuilder = new StringBuilder();
        int totalApplicants = 0;
        int counterTotalAnswers = 0;
        int radioMedian = 0;
        Map<String, String> questionAndUser = new HashMap<>();
        Map<String, Integer> question = new HashMap<>();
        Map<Integer, Integer> test = new HashMap<>();

        for (FormAnswer fa : formAnswer) {
            totalApplicants++;
            for (QuestionAnswer qa : fa.getAnswers()) {
                int matcher = 0;
                for (int i = 0; i < form.getQuestionList().size(); i++) {
                    if (form.questionList.get(form.indexCorrector(i)).getId() == qa.getQuestionId()) {

                        if (qa.getType() == 1) {
                            questionAndUser.put(fa.getUser() + qa.getQuestionId(), qa.getTextAnswer());
                            question.put(fa.getUser() + qa.getQuestionId(), qa.getQuestionId());
                        }
                        if (qa.getType() == 2) {
                            questionAndUser.put(fa.getUser() + qa.getQuestionId(), Integer.toString(qa.getRadioAnswer()));
                            question.put(fa.getUser() + qa.getQuestionId(), qa.getQuestionId());

                            radioMedian = qa.getRadioAnswer();
                            if (test.get(qa.getQuestionId()) != null) {
                                radioMedian += test.get(qa.getQuestionId());
                            }
                            test.put(qa.getQuestionId(), radioMedian);
                        }

                        if (qa.getCheckBoxAnswer() != null && !qa.getCheckBoxAnswer().isEmpty()) {
                            question.put(fa.getUser() + qa.getQuestionId(), qa.getQuestionId());

                            for (Integer o : qa.getCheckBoxAnswer()) {
                                if (form.getQuestionList().get(form.indexCorrector(i)).getCheckBoxAnswer().get(o) != null) {
                                    // checkBox.append(form.getQuestionList().get(qa.getQuestionId()).getCheckBoxAnswer().get(o) + ",");
                                    checkBox.append(form.getQ(qa.getQuestionId()).getCheckBoxAnswer().get(o) + ",");
                                }
                            }
                            String tmp = checkBox.toString();
                            tmp = tmp.replace("\n", "").replace("\r", "");

                            questionAndUser.put(fa.getUser() + qa.getQuestionId(), tmp);
                            checkBox.setLength(0);
                        }

                    }
                }
                counterTotalAnswers++;
            }
        }

        fdto.setCounterTotalAnswers(counterTotalAnswers);
        fdto.setQuestion(question);
        fdto.setQuestionAndUser(questionAndUser);
        fdto.setTest(test);
        fdto.setTotalApplicants(totalApplicants);
        fdto.setForm(form);

        for (Map.Entry<String, Integer> entry : question.entrySet()) {

            outPutBuilder.append("\n" + "Question : " + form.questionList.get(form.indexCorrector(entry.getValue())).getQuestion() + "\n");

            if (test.get(entry.getValue()) != null) {
                outPutBuilder.append("\n" + "RadioButton average = " + test.get(entry.getValue()) / totalApplicants + "\n");
            }
            outPutBuilder.append("\n" + "Answers" + "\n");

            for (Map.Entry<String, String> entry2 : questionAndUser.entrySet()) {
                if (entry.getKey().equals(entry2.getKey())) {
                    outPutBuilder.append("\n" + " | " + entry2.getValue() + " |  User : " + entry2.getKey().replaceAll("\\d", "") + "\n");

                }
            }

        }
        outPutBuilder.append("\n" + "Total Applicants :" + totalApplicants + "\n");
        outPutBuilder.append("\n" + "Total Answers :" + counterTotalAnswers + "\n");


        return fdto;
    }


}
