package com.grupo2.aulavirtual.controllers;

import com.grupo2.aulavirtual.services.EmailService;
import com.grupo2.aulavirtual.services.KeycloakService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth/password")
public class EmailController {

    @Autowired
    private EmailService emailService;


    @Autowired
    private KeycloakService keycloakService;

    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/{email}")
    public ResponseEntity<?> sendLink(@PathVariable String email) throws Exception {
        return emailService.sendVerifyEmail(email);
    }

    @PostMapping("/set-newpassword/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable String id, @RequestBody String newPassword) throws Exception {
        return emailService.setNewPasswordByEmail(id, newPassword);
    }

    @GetMapping("/send-verify-email/{email}")
    public ResponseEntity<?> sendVerifyEmail(@PathVariable String email) throws Exception {
        return emailService.sendVerifyCountEmail(email);
    }

    @GetMapping("/verify-email/{email}")
    public ResponseEntity<?> verifyCountByEmail(@PathVariable String email) throws Exception {
        return emailService.verifyCount(email);
    }
}
