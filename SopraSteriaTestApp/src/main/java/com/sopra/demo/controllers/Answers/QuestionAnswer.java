package com.sopra.demo.controllers.Answers;

import java.util.List;

public class QuestionAnswer {

    private String question;

    private int id;
    private int questionId;

    private int type;

    private String textAnswer;
    private int radioAnswer;
    private List<Integer> checkBoxAnswer;

    public QuestionAnswer(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }

    public int getRadioAnswer() {
        return radioAnswer;
    }

    public void setRadioAnswer(int radioAnswer) {
        this.radioAnswer = radioAnswer;
    }

    public List<Integer> getCheckBoxAnswer() {
        return checkBoxAnswer;
    }

    public void setCheckBoxAnswer(List<Integer> checkBoxAnswer) {
        this.checkBoxAnswer = checkBoxAnswer;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
