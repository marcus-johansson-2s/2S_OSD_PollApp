package com.sopra.demo.controllers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FinalDTO {

    FinalDTO(){}

    private Form form = new Form();
    private int totalApplicants = 0;
    private int counterTotalAnswers = 0;
    private int radioMedian = 0;
    private Map<String, String> questionAndUser = new HashMap<>();
    private Map<String,Integer> question = new HashMap<>();
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


    public String converter(String s){

        return s.replaceAll("\\d","") + "\n";

    }
}
