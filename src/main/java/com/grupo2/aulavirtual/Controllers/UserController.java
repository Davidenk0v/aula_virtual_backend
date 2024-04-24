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

    @GetMapping
    public List<AddressDTO> getAddressDTO() {

        return null;
    }

    @GetMapping("/{id}")
    public ResponseDTO<UserDTO> getUserById(@PathVariable Long id) {

        return null;
    }

    @PostMapping
    public UserDTO saveUser(@RequestBody UserEntity User) {

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {

        return null;
    }
    }

