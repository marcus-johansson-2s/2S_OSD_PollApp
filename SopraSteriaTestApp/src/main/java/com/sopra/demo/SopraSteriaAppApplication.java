package com.sopra.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = { "com.sopra.demo.DB.Entities" })
public class SopraSteriaAppApplication {


    public static void main(String[] args) {

        SpringApplication.run(SopraSteriaAppApplication.class, args);

    }

}
