package com.grupo2.aulavirtual.Controllers;

import java.util.List;

import com.grupo2.aulavirtual.Entities.AddressEntity;
import com.grupo2.aulavirtual.Payload.Request.AddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public List<AddressDTO> getAddressDTO() {

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {

        return null;
    }

    @PostMapping
    public AddressEntity saveAddress(@RequestBody AddressEntity Address) {

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long id) {

        return null;
    }

}