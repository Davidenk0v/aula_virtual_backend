package com.grupo2.aulavirtual.controllers;

import com.grupo2.aulavirtual.payload.request.UserDTO;
import com.grupo2.aulavirtual.services.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/")
    public ResponseEntity<?> getAllUserDTO() {
        return userServiceImpl.userList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userServiceImpl.findUserById(id);
    }

    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) {
        return userServiceImpl.addUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO user, @PathVariable Long id) {
        return userServiceImpl.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return userServiceImpl.deleteUser(id);
    }
}
