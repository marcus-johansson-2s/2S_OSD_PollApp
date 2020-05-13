package com.sopra.demo.controllers.Answers;

import java.util.ArrayList;
import java.util.List;

public class FormAnswer {

    private long id;
    private long formId;
    private String user;
    private boolean isAnon = false;

    private List<QuestionAnswer>  answers = new ArrayList<>();

    public  FormAnswer(){}

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getFormId() {
        return formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<QuestionAnswer> getAnswers() {
        return answers;
    }

    public void addAnswers(QuestionAnswer answers) {
        this.answers.add(answers);
    }
    public void addAllAnswers(List<QuestionAnswer> answers2) {
        answers=answers2;
    }

    public boolean isAnon() {
        return isAnon;
    }

    public void setAnon(boolean anon) {
        isAnon = anon;
    }
}
