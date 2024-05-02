package com.grupo2.aulavirtual.controllers;

import com.grupo2.aulavirtual.payload.keycloack.UserDtoKeycloack;
import com.grupo2.aulavirtual.services.KeycloackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/keycloack/user")
@PreAuthorize("hasRole('admin_aula_role')")
public class KeyCloackController {

    @Autowired
    private KeycloackService keycloackService;

    @GetMapping("/search")
    public ResponseEntity<?> findAllUsers(){
        return ResponseEntity.ok(keycloackService.findAllUsers());
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<?> findUserByUsername(@PathVariable String username){
        return ResponseEntity.ok(keycloackService.searchUserByUsername(username));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDtoKeycloack userDtoKeycloack) throws URISyntaxException {
        String response = keycloackService.createUser(userDtoKeycloack);
        return ResponseEntity.created(new URI("/keycloack/user/create")).body(response);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody UserDtoKeycloack userDtoKeycloack) throws URISyntaxException {
        keycloackService.updateUser(userId, userDtoKeycloack);
        return ResponseEntity.ok("User updated succesfully!!");
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) throws URISyntaxException {
        keycloackService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
