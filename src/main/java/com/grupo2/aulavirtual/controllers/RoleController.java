package com.grupo2.aulavirtual.controllers;

import com.grupo2.aulavirtual.payload.request.RoleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/roles")
public class RoleController {

    // @Autowired
    // private RoleService roleService;

    @GetMapping("/")
    public ResponseEntity<?> getAllRoleDTO() {

        return null;
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getRoleById(@PathVariable Long id) {

        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> saveRole(@RequestBody RoleDTO roleDTO) {

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@RequestBody RoleDTO roleDTO, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long id) {

        return null;
    }
}
