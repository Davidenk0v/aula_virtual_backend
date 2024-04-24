package com.grupo2.aulavirtual.Controllers;

import java.util.List;

import com.grupo2.aulavirtual.Payload.Request.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public ResponseEntity<?> getAllRoleDTO() {

        return null;
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getRoleById(@PathVariable Long id) {

        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> saveRole(@RequestBody RoleDTO Role) {

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@RequestBody RoleDTO Role, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long id) {

        return null;
    }
}
