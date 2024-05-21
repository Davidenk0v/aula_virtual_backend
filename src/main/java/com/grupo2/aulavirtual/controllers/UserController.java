package com.grupo2.aulavirtual.controllers;

import com.grupo2.aulavirtual.payload.request.UserDTO;
import com.grupo2.aulavirtual.services.FileService;
import com.grupo2.aulavirtual.services.KeycloakService;
import com.grupo2.aulavirtual.services.UserService;
import com.grupo2.aulavirtual.services.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Produces;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private KeycloakService keycloakService;

    @GetMapping("/")
    public ResponseEntity<?> getAllUserDTO() {
        return userServiceImpl.userList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {

        return keycloakService.searchUserById(id);
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<?> uploadFile(@PathVariable String id) {
        return fileService.sendFile(id);
    }


    @PostMapping("/file/{id}")
    public ResponseEntity<?> saveFile(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        return fileService.downloadFile(id, file);
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO user, @PathVariable String idUser,
            @PathVariable Long idTeacher, @RequestParam(name = "file", required = false) MultipartFile file) {
        return keycloakService.updateUser(idUser, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id) {
        return keycloakService.deleteUser(id);
    }

    @DeleteMapping("/file/{id}")
    public ResponseEntity<?> setDefaultImage(@PathVariable String id) {
        return fileService.setDefaultImage(id);
    }

}
