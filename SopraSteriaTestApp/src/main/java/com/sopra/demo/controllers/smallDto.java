package com.sopra.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class smallDto {
    private String adminPass="";


    public String getAdminPass() {
        return adminPass;
    }
    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }

    Map<Integer,String> descrption = new HashMap<>();

    Map<Integer,String> strings = new HashMap<>();
    List<Integer> nummer = new ArrayList<>();

    smallDto(String a){adminPass=a;}
    smallDto(){}



    public List<Integer> getNummer() {
        return nummer;
    }

    public void addInt(int lista) {
        this.nummer.add(lista);
    }


    public String getSpecial(int i){
        return strings.get(i);
    }

    public Map<Integer, String> getStrings() {
        return strings;
    }

    public void addStrings(int a, String b) {
        this.strings.put(a,b);
    }

    public void addDesc(int a, String b) {
        this.descrption.put(a,b);
    }
    public Map<Integer, String> getDesc() {
        return descrption;
    }

    public String getSpecDesc(int index){

        String test="";

        for(Map.Entry<Integer,String>entry:descrption.entrySet()){

            if(entry.getKey()==index){
                test=entry.getValue();
                return test;
            }

        }


        return test;

    }
}
