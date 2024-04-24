package com.grupo2.aulavirtual.Controllers;

import java.util.List;

import com.grupo2.aulavirtual.Entities.RoleEntity;
import com.grupo2.aulavirtual.Entities.RolleEntity;
import com.grupo2.aulavirtual.Payload.Request.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<RoleDTO> getRoleDTO() {

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {

        return null;
    }

    @PostMapping
    public RoleEntity saveRole(@RequestBody RoleEntity Role) {

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long id) {

        return null;
    }
}

