package com.sopra.demo.controllers;

import com.sopra.demo.controllers.Answers.FormAnswer;
import com.sopra.demo.controllers.Answers.QuestionAnswer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

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
                int matcher=0;
                for(int i=0;i<form.getQuestionList().size();i++){
                    if(form.getQuestionList().get(i).getId()==qa.getId())
                    {
                        matcher=i;
                    }
                }
                counterTotalAnswers++;
                if(fa.getFormId()==form.getFormId()) {
                    outPutBuilder.append(" Question :" + form.getQuestionList().get(matcher).getQuestion()+ "\n");
                    if (qa.getType() == 1)
                        outPutBuilder.append("\n" + "Text" + "\n" + qa.getTextAnswer() + "\n");
                    if (qa.getType() == 2) {
                        outPutBuilder.append("\n" + "Radio :" + qa.getRadioAnswer() + "\n");
                            radioMedian=qa.getRadioAnswer();

                            if(test.get(matcher) != null)
                            radioMedian+=test.get(matcher);

                            test.put(matcher,radioMedian);
                    }
                    if (qa.getCheckBoxAnswer() != null && !qa.getCheckBoxAnswer().isEmpty()) {
                        outPutBuilder.append("\n"+"Checkbox: ");
                        for (Integer o : qa.getCheckBoxAnswer()) {
                            if(form.getQuestionList().get(matcher).getCheckBoxAnswer().get(o)!=null)
                            outPutBuilder.append("\n"+form.getQuestionList().get(qa.getQuestionId()).getCheckBoxAnswer().get(o) + "  " );
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



    public String SuperOut2() {

        StringBuilder checkBox = new StringBuilder();
        StringBuilder outPutBuilder = new StringBuilder();
        int totalApplicants = 0;
        int counterTotalAnswers = 0;
        int radioMedian = 0;

        Map<String, String> questionAndUser = new HashMap<>();
        Map<String,Integer> question = new HashMap<>();
        Map<Integer, Integer> test = new HashMap<>();

        for (FormAnswer fa : formAnswer) {
            totalApplicants++;
            for (QuestionAnswer qa : fa.getAnswers()) {
                int matcher = 0;
                for (int i = 0; i < form.getQuestionList().size(); i++) {
                    if (form.questionList.get(form.indexCorrector(i)).getId() == qa.getQuestionId()) {

                        if(qa.getType()==1) {
                            questionAndUser.put(fa.getUser() + qa.getQuestionId(),qa.getTextAnswer());
                            question.put( fa.getUser()+qa.getQuestionId(),qa.getQuestionId());
                        }
                        if(qa.getType()==2) {
                            questionAndUser.put(fa.getUser() + qa.getQuestionId(),Integer.toString(qa.getRadioAnswer()));
                            question.put( fa.getUser()+qa.getQuestionId(),qa.getQuestionId());

                            radioMedian=qa.getRadioAnswer();
                            if(test.get(qa.getQuestionId()) != null) {
                                radioMedian += test.get(qa.getQuestionId());
                            }
                            test.put(qa.getQuestionId(),radioMedian);
                        }

                        if (qa.getCheckBoxAnswer() != null && !qa.getCheckBoxAnswer().isEmpty()) {
                            question.put(fa.getUser()+qa.getQuestionId(),qa.getQuestionId());

                            for (Integer o : qa.getCheckBoxAnswer()) {
                                if(form.getQuestionList().get(form.indexCorrector(i)).getCheckBoxAnswer().get(o)!=null) {
                                    checkBox.append(form.getQuestionList().get(qa.getQuestionId()).getCheckBoxAnswer().get(o) + ",");
                                }
                            }
                            String tmp=checkBox.toString();
                            tmp=tmp.replace("\n", "").replace("\r", "");

                            questionAndUser.put(fa.getUser() + qa.getQuestionId(),tmp);
                            checkBox.setLength(0);
                        }

                    }
                }
                counterTotalAnswers++;
            }
        }

        outPutBuilder.append("\n" + "-----------------------------------" + "\n");
        outPutBuilder.append("\n" + "Form :" + form.getFormId() + "\n");
        outPutBuilder.append("\n" + "-----------------------------------" + "\n");
        for (Map.Entry<String,Integer> entry : question.entrySet()) {

            outPutBuilder.append("\n" + "Question : "+form.questionList.get(form.indexCorrector(entry.getValue())).getQuestion() + "\n");

            if( test.get(entry.getValue())!=null) {
                outPutBuilder.append("\n" + "RadioButton average = " + test.get(entry.getValue())/totalApplicants + "\n");
            }
            outPutBuilder.append("\n" + "Answers" +"\n");

                for (Map.Entry<String, String> entry2 : questionAndUser.entrySet()) {
                    if (entry.getKey().equals(entry2.getKey())) {
                        outPutBuilder.append("\n"+ " | " + entry2.getValue() + " |  User : " + entry2.getKey().replaceAll("\\d","") + "\n");

                    }
                }
                outPutBuilder.append("\n" + "-----------------------------------" + "\n");
        }
        outPutBuilder.append("\n"+ "Total Applicants :"+ totalApplicants + "\n");
        outPutBuilder.append("\n"+ "Total Answers :"+ counterTotalAnswers + "\n");




        System.out.println("-..............................................");
        for (Map.Entry<String, String> entry : questionAndUser.entrySet()) {
            System.out.println("User map "+ entry.getKey() + "  :  " + entry.getValue());


        }

        for (Map.Entry<String,Integer> entry : question.entrySet()) {
            System.out.println("question map "+ entry.getKey() + "  :  " + entry.getValue());
        }
        System.out.println("-..............................................");


        return outPutBuilder.toString();
    }



    public FinalDTO SuperOut3() {

        FinalDTO fdto = new FinalDTO();

        StringBuilder checkBox = new StringBuilder();
        StringBuilder outPutBuilder = new StringBuilder();
        int totalApplicants = 0;
        int counterTotalAnswers = 0;
        int radioMedian = 0;
        Map<String, String> questionAndUser = new HashMap<>();
        Map<String,Integer> question = new HashMap<>();
        Map<Integer, Integer> test = new HashMap<>();

        for (FormAnswer fa : formAnswer) {
            totalApplicants++;
            for (QuestionAnswer qa : fa.getAnswers()) {
                int matcher = 0;
                for (int i = 0; i < form.getQuestionList().size(); i++) {
                    if (form.questionList.get(form.indexCorrector(i)).getId() == qa.getQuestionId()) {

                        if(qa.getType()==1) {
                            questionAndUser.put(fa.getUser() + qa.getQuestionId(),qa.getTextAnswer());
                            question.put( fa.getUser()+qa.getQuestionId(),qa.getQuestionId());
                        }
                        if(qa.getType()==2) {
                            questionAndUser.put(fa.getUser() + qa.getQuestionId(),Integer.toString(qa.getRadioAnswer()));
                            question.put( fa.getUser()+qa.getQuestionId(),qa.getQuestionId());

                            radioMedian=qa.getRadioAnswer();
                            if(test.get(qa.getQuestionId()) != null) {
                                radioMedian += test.get(qa.getQuestionId());
                            }
                            test.put(qa.getQuestionId(),radioMedian);
                        }

                        if (qa.getCheckBoxAnswer() != null && !qa.getCheckBoxAnswer().isEmpty()) {
                            question.put(fa.getUser()+qa.getQuestionId(),qa.getQuestionId());

                            for (Integer o : qa.getCheckBoxAnswer()) {
                                if(form.getQuestionList().get(form.indexCorrector(i)).getCheckBoxAnswer().get(o)!=null) {
                                    checkBox.append(form.getQuestionList().get(qa.getQuestionId()).getCheckBoxAnswer().get(o) + ",");
                                }
                            }
                            String tmp=checkBox.toString();
                            tmp=tmp.replace("\n", "").replace("\r", "");

                            questionAndUser.put(fa.getUser() + qa.getQuestionId(),tmp);
                            checkBox.setLength(0);
                        }

                    }
                }
                counterTotalAnswers++;
            }
        }


        outPutBuilder.append("\n" + "-----------------------------------" + "\n");
        outPutBuilder.append("\n" + "Form :" + form.getFormId() + "\n");
        outPutBuilder.append("\n" + "-----------------------------------" + "\n");
        for (Map.Entry<String,Integer> entry : question.entrySet()) {

            outPutBuilder.append("\n" + "Question : "+form.questionList.get(form.indexCorrector(entry.getValue())).getQuestion() + "\n");

            if( test.get(entry.getValue())!=null) {
                outPutBuilder.append("\n" + "RadioButton average = " + test.get(entry.getValue())/totalApplicants + "\n");
            }
            outPutBuilder.append("\n" + "Answers" +"\n");

            for (Map.Entry<String, String> entry2 : questionAndUser.entrySet()) {
                if (entry.getKey().equals(entry2.getKey())) {
                    outPutBuilder.append("\n"+ " | " + entry2.getValue() + " |  User : " + entry2.getKey().replaceAll("\\d","") + "\n");

                }
            }
            outPutBuilder.append("\n" + "-----------------------------------" + "\n");
        }
        outPutBuilder.append("\n"+ "Total Applicants :"+ totalApplicants + "\n");
        outPutBuilder.append("\n"+ "Total Answers :"+ counterTotalAnswers + "\n");



       // return outPutBuilder.toString();
        return fdto;
    }



}
