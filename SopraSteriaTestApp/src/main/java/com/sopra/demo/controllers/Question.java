package com.sopra.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question {
    private String question;
    private int id;
    private int typeQuestion;

    private int tmpInt=0;
    private String tmpString;
    private Map<Integer, String> checkBoxAnswer= new HashMap<>();

    private List<Integer> checkBoxAnswerList = new ArrayList<>();


    Question(){}

    public Question(String question, int id,int typeQuestion) {
        this.question = question;
        this.id = id;
        this.typeQuestion=typeQuestion;
    }


    public List<Integer> getCheckBoxAnswerList() {
        return checkBoxAnswerList;
    }

    public void setCheckBoxAnswerList(int checkBoxAnswerList) {
        this.checkBoxAnswerList.add(checkBoxAnswerList);
    }

    public Map<Integer, String> getCheckBoxAnswer() {
        return checkBoxAnswer;
    }

    public void setCheckBoxAnswer(int key, String checkBoxAnswer) {
        this.checkBoxAnswer.put(key, checkBoxAnswer);
    }

    public int getTypeQuestion() {
        return typeQuestion;
    }

    public void setTypeQuestion(int typeQuestion) {
        this.typeQuestion = typeQuestion;
    }



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTmpInt() {
        return tmpInt;
    }

    public void setTmpInt(int tmpInt) {
        this.tmpInt = tmpInt;
    }

    public String getTmpString() {
        return tmpString;
    }

    public void setTmpString(String tmpString) {
        this.tmpString = tmpString;
    }


}
