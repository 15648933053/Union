package com.starcpdk.edu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.starcpdk.edu"})
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class , args);
    }
}
