package com.sopra.demo.DB.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "form")
public class FormDB implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "formid", unique = true)
    private int formId;
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "isanon", nullable = true)
    private int isAnon;

    public int getId() {
        return formId;
    }

    public void setId(int id) {
        this.formId = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsAnon() {
        return isAnon;
    }

    public void setIsAnon(boolean test) {

        if(test)
            isAnon=1;
        else
            isAnon=0;

    }
}
