package com.sopra.demo.controllers;

import com.sopra.demo.controllers.Answers.FormAnswer;
import com.sopra.demo.controllers.Answers.QuestionAnswer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DtoFormAnswers {

    private Form form= new Form();
    private List<FormAnswer> formAnswer= new ArrayList<>();

    public DtoFormAnswers(){}


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


    public String SuperOut(){
        StringBuilder outPutBuilder = new StringBuilder();
        int totalApplicants=0;
        int counterTotalAnswers=0;
        Map<Integer,Integer> test = new HashMap<>();
        int radioMedian=0;
        for(FormAnswer fa:formAnswer){
            totalApplicants++;
            outPutBuilder.append("\n"+ "-----------------------------------"+"\n");
            outPutBuilder.append("\n"+ "User :"+fa.getUser()+"\n");
            for(QuestionAnswer qa:fa.getAnswers()){
                counterTotalAnswers++;
                if(fa.getFormId()==form.getFormId()) {
                    outPutBuilder.append(" Question :" + form.getQuestionList().get(qa.getQuestionId()).getQuestion()+ "\n");
                    if (qa.getType() == 1)
                        outPutBuilder.append("\n" + "TextBox" + "\n" + qa.getTextAnswer() + "\n");
                    if (qa.getType() == 2) {
                        outPutBuilder.append("\n" + "RadioAnswer :" + qa.getRadioAnswer() + "\n");
                            radioMedian=qa.getRadioAnswer();

                            if(test.get(qa.getQuestionId()) != null)
                            radioMedian+=test.get(qa.getQuestionId());

                            test.put(qa.getQuestionId(),radioMedian);
                    }
                    if (qa.getCheckBoxAnswer() != null && !qa.getCheckBoxAnswer().isEmpty()) {
                        outPutBuilder.append("\n"+"CheckBoxAnswer: ");
                        for (Integer o : qa.getCheckBoxAnswer()) {
                            if(form.getQuestionList().get(qa.getQuestionId()).getCheckBoxAnswer().get(o)!=null)
                            outPutBuilder.append(form.getQuestionList().get(qa.getQuestionId()).getCheckBoxAnswer().get(o) + "  " );
                        }
                    }
                    outPutBuilder.append("\n"+ "-"+ "\n");
                }
            }

        }
        outPutBuilder.append("\n"+ "-------------------"+"\n");
        outPutBuilder.append("\n"+ "Total Applicants :"+ totalApplicants + "\n");
        outPutBuilder.append("\n"+ "Total Answers :"+ counterTotalAnswers + "\n");
        outPutBuilder.append("\n"+ "RadioButton Averages "+ "\n");
        int finalTotalApplicants = totalApplicants;
        DecimalFormat df = new DecimalFormat("#.00");
        test.forEach((key, value) -> outPutBuilder.append("\n"+"Question :" +form.getQuestionList().get(key).getQuestion() + " average :" + df.format((float)value/finalTotalApplicants) +"\n"));
        outPutBuilder.append("\n"+ "-------------------"+"\n");

        return outPutBuilder.toString();
    }
}