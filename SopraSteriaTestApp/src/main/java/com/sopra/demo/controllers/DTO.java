package com.sopra.demo.controllers;

public class DTO {
    private String question;
    private String answer;
    private int id;
    private int answerId;
    private int formId;
    private int anotherQuestion=0;
    private String checkBoxText;
    private boolean tmpInt=false;

    DTO(){}
    public DTO(String answer, int id) {
        this.answer = answer;
        this.id = id;

    }


    public String getCheckBoxText() {
        return checkBoxText;
    }

    public void setCheckBoxText(String checkBoxText) {
        this.checkBoxText = checkBoxText;
    }

    public int getAnotherQuestion() {
        return anotherQuestion;
    }

    public void setAnotherQuestion(int anotherQuestion) {
        this.anotherQuestion = anotherQuestion;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getTmpInt() {
        return tmpInt;
    }

    public void setTmpInt(boolean tmpInt) {
        this.tmpInt = tmpInt;
    }
}
