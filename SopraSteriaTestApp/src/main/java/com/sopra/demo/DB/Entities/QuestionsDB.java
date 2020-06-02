package com.sopra.demo.DB.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "questions4forms")
public class QuestionsDB implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private int id;
    @Column(name = "formid")
    private int formId;
    @Column(name = "questionid")
    private int questionId;
    @Column(name = "question", nullable = true)
    private String question;
    @Column(name = "type", nullable = true)
    private int questiontype;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(int questiontype) {
        this.questiontype = questiontype;
    }
}