package com.sopra.demo.controllers;

import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class Form {

    private Long formId;
    public List<Question> questionList=new ArrayList<>();
    private String description;


    Form(){}

    Form(long formId, String desc){
        this.formId = formId;
        description=desc;

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
}
