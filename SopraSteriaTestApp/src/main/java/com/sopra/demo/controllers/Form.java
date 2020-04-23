package com.sopra.demo.controllers;

import java.util.ArrayList;
import java.util.List;

public class Form {

   private int formId;
    private List<Question> questionList=new ArrayList<>();
    private String description;




    Form(){}

    Form(int formId, String desc){
        this.formId = formId;
        description=desc;

    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(Question questionList) {
        this.questionList.add(questionList);
    }
}
