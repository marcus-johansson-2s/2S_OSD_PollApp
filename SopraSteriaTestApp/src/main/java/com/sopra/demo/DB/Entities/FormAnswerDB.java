package com.sopra.demo.DB.Entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "answers")
public class FormAnswerDB implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private int id;
    @Column(name = "formid")
    private int formId;
    @Column(name = "questionid")
    private int questionId;
    @Column(name = "answer", nullable = true)
    private String answer;
    @Column(name = "user", nullable = true)
    private String user;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
