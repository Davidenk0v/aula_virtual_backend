package com.grupo2.aulavirtual.controllers;

import com.grupo2.aulavirtual.entities.UserEntity;
import com.grupo2.aulavirtual.mappers.DtoMapper;
import com.grupo2.aulavirtual.payload.request.UserDTO;
import com.grupo2.aulavirtual.repositories.UserRepository;
import com.grupo2.aulavirtual.services.KeycloakService;
import com.grupo2.aulavirtual.util.email.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth/password")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeycloakService keycloakService;

    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/{email}")
    public ResponseEntity<?> sendLink(@PathVariable String email) throws Exception {

        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);
        if (optionalUserEntity.isPresent()) {
            UserEntity user = optionalUserEntity.get();
            emailService.sendPasswordResetEmail(email, user.getIdKeycloak());
            Map<String, String> response = new HashMap<>();
            response.put("OK", "Se ha enviado el correo a su email");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>("No existe ese email", HttpStatus.NOT_FOUND);

    }

    @PostMapping("/set-newpassword/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable String id, @RequestBody String newPassword) throws Exception {
        Optional<UserEntity> userOptional = userRepository.findByIdKeycloak(id);
        if (userOptional.isPresent()) {
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
        return new ResponseEntity<>("Error al encontrar actualizar la contraseña", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/send-verify-email/{email}")
    public ResponseEntity<?> sendVerifyEmail(@PathVariable String email) throws Exception {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserDTO user = modelMapper.map(userOptional.get(), UserDTO.class);
            try {
                emailService.verfifyEmail(email, userOptional.get().getIdKeycloak());
            } catch (Exception e) {
                return new ResponseEntity<>("Error al verficar email", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Map<String, String> response = new HashMap<>();
            response.put("OK", "Se ha enviado el correo correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Error al enviar el correo de verificación", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/verify-email/{email}")
    public ResponseEntity<?> verifyEmail(@PathVariable String email) throws Exception {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
                Map<String, String> response = new HashMap<>();
        if (userOptional.isPresent()) {
            UserDTO user = modelMapper.map(userOptional.get(), UserDTO.class);
            try {
                keycloakService.updateUser(userOptional.get().getIdKeycloak(), user);
                response.put("OK", "Correo verificado correctamente");


            } catch (Exception e) {
                return new ResponseEntity<>("Error al verficar email", HttpStatus.INTERNAL_SERVER_ERROR);
            }


        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
