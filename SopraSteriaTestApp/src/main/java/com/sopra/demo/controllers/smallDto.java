package com.sopra.demo.controllers;

public class smallDto {
    private String adminPass="";


    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }

    smallDto(String a){adminPass=a;}
    smallDto(){}


}
