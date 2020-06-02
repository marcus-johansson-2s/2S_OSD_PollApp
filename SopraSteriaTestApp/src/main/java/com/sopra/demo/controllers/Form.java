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
    private boolean active=false;


   public Form(){}

    public Form(long formId, String desc,boolean anon,boolean bool){
        this.formId = formId;
        description=desc;
        isAnon=anon;
        active = bool;

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

    public void listSetter(List<Question> questionList2) {
/*
        for(int i =0;i<questionList.size();i++){
           questionList.get(i).setQuestion(questionList2.get(i).getQuestion());

        }

 */

        questionList=questionList2;

    }

    public boolean getAnon() {
        return isAnon;
    }

    public void setAnon(boolean anon) {
        isAnon = anon;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


       public int indexCorrector(int index) {
            for (int i = 0; i < questionList.size(); i++) {

               if (questionList.get(i).getId() == index) {
                   return i;
               }

           }
           return 0;
    }
}
