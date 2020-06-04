package com.sopra.demo.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinalDTO {

    FinalDTO() {
    }

    private Form form = new Form();
    private int totalApplicants = 0;
    private int counterTotalAnswers = 0;
    private int radioMedian = 0;
    private Map<String, String> questionAndUser = new HashMap<>();
    private Map<String, Integer> question = new HashMap<>();
    private Map<Integer, Integer> test = new HashMap<>();

    //Resource resource = new ClassPathResource("exel/temp.xlsx");


    public int getTotalApplicants() {
        return totalApplicants;
    }

    public void setTotalApplicants(int totalApplicants) {
        this.totalApplicants = totalApplicants;
    }

    public int getCounterTotalAnswers() {
        return counterTotalAnswers;
    }

    public void setCounterTotalAnswers(int counterTotalAnswers) {
        this.counterTotalAnswers = counterTotalAnswers;
    }

    public int getRadioMedian() {
        return radioMedian;
    }

    public void setRadioMedian(int radioMedian) {
        this.radioMedian = radioMedian;
    }

    public Map<String, String> getQuestionAndUser() {
        return questionAndUser;
    }

    public void setQuestionAndUser(Map<String, String> questionAndUser) {
        this.questionAndUser = questionAndUser;
    }

    public Map<String, Integer> getQuestion() {
        return question;
    }

    public void setQuestion(Map<String, Integer> question) {
        this.question = question;
    }

    public Map<Integer, Integer> getTest() {
        return test;
    }

    public void setTest(Map<Integer, Integer> test) {
        this.test = test;
    }


    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }


    public String converter(String s) {

        return s.replaceAll("\\d", "") + "\n";

    }

    public List<Integer> listgetter() {
        List<Integer> tmpList = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : question.entrySet()) {

            if (!tmpList.contains(entry.getValue()))
                tmpList.add(entry.getValue());
        }

        return tmpList;
    }
}
