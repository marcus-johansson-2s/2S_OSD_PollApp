package com.sopra.demo.DB.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "checkboxanswers")
public class CheckboxAnswer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private int id;
    @Column(name = "idanswer")
    private int idanswer;
    @Column(name = "user")
    private String user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdanswer() {
        return idanswer;
    }

    public void setIdanswer(int idanswer) {
        this.idanswer = idanswer;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}