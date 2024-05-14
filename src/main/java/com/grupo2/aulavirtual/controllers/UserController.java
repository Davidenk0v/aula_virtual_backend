package com.grupo2.aulavirtual.controllers;

import com.grupo2.aulavirtual.payload.request.UserDTO;
import com.grupo2.aulavirtual.services.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/file/{id}")
    public ResponseEntity<?> uploadFile(@PathVariable Long id) {
        return userServiceImpl.sendFile(id);
    }

    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) {
        return userServiceImpl.addUser(user);
    }

    @PostMapping("/file/{id}")
    public ResponseEntity<?> saveFile(@PathVariable Long id,@RequestParam("file") MultipartFile file) {
        return userServiceImpl.downloadFile(id, file);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO user, @PathVariable Long id) {
        return userServiceImpl.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return userServiceImpl.deleteUser(id);
    }

    @DeleteMapping("/file/{id}")
    public ResponseEntity<?> setDefaultImage(@PathVariable Long id) {
        System.out.println();
        System.out.println("LLAMADA IMAGEN POR DEFECTO");
        System.out.println();
        return userServiceImpl.setDefaultImage(id);
    }

    @GetMapping("/listaTeacher/{id}")
    public ResponseEntity<?> getListaTeacherByID(@PathVariable Long id) {
        return userServiceImpl.userCoursesList(id);
    }
}
