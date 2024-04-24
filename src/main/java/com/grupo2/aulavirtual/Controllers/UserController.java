package com.grupo2.aulavirtual.Controllers;

import java.util.List;

import com.grupo2.aulavirtual.Entities.UserEntity;
import com.grupo2.aulavirtual.Payload.Request.AddressDTO;
import com.grupo2.aulavirtual.Payload.Request.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getAllUserDTO() {

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {

        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO User) {

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO User, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {

        return null;
    }
}
