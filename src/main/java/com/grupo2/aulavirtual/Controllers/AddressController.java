package com.grupo2.aulavirtual.Controllers;

import java.util.List;

import com.grupo2.aulavirtual.Entities.AddressEntity;
import com.grupo2.aulavirtual.Payload.Request.AddressDTO;


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