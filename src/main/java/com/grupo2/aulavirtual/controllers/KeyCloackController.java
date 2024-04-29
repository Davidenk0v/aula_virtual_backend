package com.grupo2.aulavirtual.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeyCloackController {

    @PreAuthorize("hasRole('admin_aula_role')")
    @GetMapping("/hello-1")
    public String hello1(){
        return "Hello with keycloack - ADMIN";
    }

    @PreAuthorize("hasRole('student_aula_role')")
    @GetMapping("/hello-2")
    public String hello2(){
        return "Hello with keycloack - USER";
    }
}
