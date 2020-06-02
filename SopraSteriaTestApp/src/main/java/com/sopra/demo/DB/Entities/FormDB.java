package com.sopra.demo.DB.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "form")
public class FormDB implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "formid", unique = true)
    private int formId;
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "isanon", nullable = true)
    private int isAnon;
    @Column(name = "isactive", nullable = true)
    private int isActive;

    public int getFormId() {
        return formId;
    }

    public void setFormId(int id) {
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

    public void setIsActive(boolean test) {

        if(test)
            isActive=1;
        else
            isActive=0;

    }

    public int getIsActive() {
        return isActive;
    }
}
