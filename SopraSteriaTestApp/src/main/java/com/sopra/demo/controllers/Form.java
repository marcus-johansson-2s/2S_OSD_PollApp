package com.sopra.demo.controllers;

import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class Form {

    private Long formId;
    public List<Question> questionList=new ArrayList<>();
    private String description;
    private boolean isAnon=false;


    Form(){}

    Form(long formId, String desc,boolean anon){
        this.formId = formId;
        description=desc;
        isAnon=anon;

    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getFormId() {
        return formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(Question questionList) {
        this.questionList.add(questionList);
    }

    public boolean getAnon() {
        return isAnon;
    }

    public void setAnon(boolean anon) {
        isAnon = anon;
    }
}
