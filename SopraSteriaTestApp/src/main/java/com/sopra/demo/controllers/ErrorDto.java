package com.sopra.demo.controllers;

public class ErrorDto {

    private String message;

    public ErrorDto(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorDto(String message) {
        this.message = message;
    }
}
