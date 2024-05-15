package com.grupo2.aulavirtual.util.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;
    private String url = "http://localhost:4200/new-password";
    private String urlVerify = "http://localhost:4200/verify-done";

    public void sendPasswordResetEmail(String to, String id) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("noreply@aulavirtual.com");
            helper.setTo(to);
            helper.setSubject("Recuperación de contraseña");
            String htmlContent = "<p>Por favor, haga clic en el siguiente enlace para restablecer su contraseña:</p>" +
                    "<a href='" + url + "/" + id + "'>Restablecer contraseña</a>";
            helper.setText(htmlContent, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void verfifyEmail(String to, String id) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("noreply@aulavirtual.com");
            helper.setTo(to);
            helper.setSubject("Verificación de cuenta");
            String htmlContent = "<p>Por favor, haga clic en el siguiente enlace para verificar su cuenta:</p>" +
                    "<a href='" + urlVerify + "/" + to + "'>Verificar cuenta</a>";
            helper.setText(htmlContent, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}

