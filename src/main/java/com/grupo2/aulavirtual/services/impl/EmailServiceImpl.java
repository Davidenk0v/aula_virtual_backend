package com.grupo2.aulavirtual.services.impl;


import com.grupo2.aulavirtual.payload.request.UserDTO;
import com.grupo2.aulavirtual.services.EmailService;
import com.grupo2.aulavirtual.services.KeycloakService;
import com.grupo2.aulavirtual.util.mappers.DtoMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {

    DtoMapper dtoMapper = new DtoMapper();

    @Autowired
    private KeycloakService keycloakService;

    @Autowired
    private JavaMailSender emailSender;

    private final String URL_NEW_PASSWORD = "http://localhost:4200/new-password";

    private final String URL_VERIFY = "http://localhost:4200/verify-done";

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public ResponseEntity<?> sendVerifyEmail(String email) throws Exception {

        UserRepresentation userRepresentation = keycloakService.findUserByEmail(email);
        if (userRepresentation != null) {
            sendPasswordResetEmail(email, userRepresentation.getId());
            Map<String, String> response = new HashMap<>();
            response.put("OK", "Se ha enviado el correo a su email");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>("No existe ese email", HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<?> setNewPasswordByEmail(String id, String newPassword) throws Exception {
        UserRepresentation userRepresentation = keycloakService.findUserById(id);
        if (userRepresentation != null) {
            UserDTO user = new UserDTO().builder()
                    .password(newPassword)
                    .build();
            try {
                keycloakService.updateUser(id, user);

            } catch (Exception e) {
                return new ResponseEntity<>("Error al cambiar la contraseña", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Map<String, String> response = new HashMap<>();
            response.put("OK", "Contraseña actualizada correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Error al encontrar el usuario con ese email", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> sendVerifyCountEmail(String email) throws Exception {
        UserRepresentation userRepresentation = keycloakService.findUserByEmail(email);
        if (userRepresentation != null) {
            try {
                verfifyEmail(email, userRepresentation.getId());
            } catch (Exception e) {
                return new ResponseEntity<>("Error al verficar email", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Map<String, String> response = new HashMap<>();
            response.put("OK", "Se ha enviado el correo correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Error al enviar el correo de verificación", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> verifyCount(String email) throws Exception {
        UserRepresentation userRepresentation = keycloakService.findUserByEmail(email);
        Map<String, String> response = new HashMap<>();
        if (userRepresentation != null) {
            UserDTO user = dtoMapper.userRepresentationToDto(userRepresentation);
            try {
                keycloakService.updateUser(userRepresentation.getId(), user);
                response.put("OK", "Correo verificado correctamente");


            } catch (Exception e) {
                return new ResponseEntity<>("Error al verficar email", HttpStatus.INTERNAL_SERVER_ERROR);
            }


        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public void sendPasswordResetEmail(String to, String id) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("noreply@aulavirtual.com");
            helper.setTo(to);
            helper.setSubject("Recuperación de contraseña");
            String htmlContent = "<p>Por favor, haga clic en el siguiente enlace para restablecer su contraseña:</p>" +
                    "<a href='" + URL_NEW_PASSWORD + "/" + id + "'>Restablecer contraseña</a>";
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
                    "<a href='" + URL_VERIFY + "/" + to + "'>Verificar cuenta</a>";
            helper.setText(htmlContent, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}

