package com.sopra.demo.controllers;

import java.util.ArrayList;
import java.util.List;

public class Member {

    private String name;
    private String password;


    Member(){};

    Member(String a,String b){name=a;password=b;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}'; }
}
