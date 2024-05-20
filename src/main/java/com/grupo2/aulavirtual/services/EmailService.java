package com.grupo2.aulavirtual.services;

import org.springframework.http.ResponseEntity;

public interface EmailService {

    ResponseEntity<?> sendVerifyEmail(String email) throws Exception;

    ResponseEntity<?> setNewPasswordByEmail(String id, String newPassword) throws Exception;

    ResponseEntity<?> sendVerifyCountEmail(String email) throws Exception;

    ResponseEntity<?> verifyCount(String email) throws Exception;

    void sendPasswordResetEmail(String to, String id) ;

    void verfifyEmail(String to, String id);
}
