package com.grupo2.aulavirtual.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello-1")
    public String helloAdmin(){
        return "Hello Spring Boot - ADMIN";
    }

    @GetMapping("/hello-2")
    public String helloUser(){
        return "Hello Spring Boot - USERS";
    }
}
