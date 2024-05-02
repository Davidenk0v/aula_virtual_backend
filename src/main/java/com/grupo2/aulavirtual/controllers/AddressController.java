package com.grupo2.aulavirtual.controllers;

import com.grupo2.aulavirtual.payload.request.AddressDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/address")
public class AddressController {

    @GetMapping("/")
    public ResponseEntity<?> getAllddressDTO() {

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable Long id) {

        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> saveAddress(@RequestBody AddressDTO addressDTO) {

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdress(@RequestBody AddressDTO addressDTO, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddressById(@PathVariable Long id) {

        return null;
    }

}