package com.grupo2.aulavirtual.Controllers;

import java.util.List;

import com.grupo2.aulavirtual.Payload.Request.AddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/address")
public class AddressController {

    // @Autowired
    // private AddressService addressService;

    @GetMapping("/")
    public ResponseEntity<?> getAllddressDTO() {

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable Long id) {

        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> saveAddress(@RequestBody AddressDTO Address) {

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdress(@RequestBody AddressDTO Address, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddressById(@PathVariable Long id) {

        return null;
    }

}