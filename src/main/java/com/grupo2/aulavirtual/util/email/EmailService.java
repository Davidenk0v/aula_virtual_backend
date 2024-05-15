package com.grupo2.aulavirtual.util.email;

public interface EmailService {

    public void sendPasswordResetEmail(String to, String id) ;

    void verfifyEmail(String to, String id);
}
