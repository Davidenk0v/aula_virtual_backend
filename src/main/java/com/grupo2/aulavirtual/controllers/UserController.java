package com.grupo2.aulavirtual.controllers;

import com.grupo2.aulavirtual.payload.request.UserDTO;
import com.grupo2.aulavirtual.services.UserService;
import com.grupo2.aulavirtual.services.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userServiceImpl;

    @GetMapping("/")
    public ResponseEntity<?> getAllUserDTO() {
        return userServiceImpl.userList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            String token = authorizationHeader.substring(7);

        } else {
            logger.info("No se ha proporcionado un token de acceso válido.");
        }

        return userServiceImpl.findUserById(id);
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<?> uploadFile(@PathVariable Long id) {
        return userServiceImpl.sendFile(id);
    }

    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user, @PathVariable Long idTeacher,
            @RequestParam(name = "file", required = false) MultipartFile file) {
        return userServiceImpl.addUser(user, file);
    }

    @PostMapping("/file/{id}")
    public ResponseEntity<?> saveFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return userServiceImpl.downloadFile(id, file);
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO user, @PathVariable String idUser,
            @PathVariable Long idTeacher, @RequestParam(name = "file", required = false) MultipartFile file) {
        return userServiceImpl.updateUser(user, idUser, file);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return userServiceImpl.deleteUser(id);
    }

    @DeleteMapping("/file/{id}")
    public ResponseEntity<?> setDefaultImage(@PathVariable Long id) {
        return userServiceImpl.setDefaultImage(id);
    }

    @GetMapping("/listaTeacher/{email}")
    public ResponseEntity<?> getListaTeacherByEmail(@PathVariable String email) {
        return userServiceImpl.userCoursesList(email);
    }
}
