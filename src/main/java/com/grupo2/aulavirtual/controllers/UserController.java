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


@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = "*"
)
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

    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) {
        return userServiceImpl.addUser(user);
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO user, @PathVariable String idUser) {
        return userServiceImpl.updateUser(user, idUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return userServiceImpl.deleteUser(id);
    }

    @GetMapping("/listaTeacher/{idUser}")
    public ResponseEntity<?> getListaTeacherByEmail(@PathVariable String idUser) {
        return userServiceImpl.userCoursesList(idUser);
    }
}
