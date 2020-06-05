package com.sopra.demo.controllers;

public class SuccessDto {

    private String message;

    public SuccessDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SuccessDto(String message) {
        this.message = message;
    }
}
