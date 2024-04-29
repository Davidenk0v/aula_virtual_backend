package com.grupo2.aulavirtual.tests.dtotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.grupo2.aulavirtual.Payload.Request.RoleDTO;
import org.junit.jupiter.api.Test;

import com.grupo2.aulavirtual.Enum.RoleEnum;


public class RoleDTOTest {
    @Test
    void testGettersAndSetters() {
        RoleDTO roleDTO = RoleDTO.builder()
            .idRole(1L)
            .role(RoleEnum.ADMIN)
            .user(null)
            .build();

        assertEquals(1L, roleDTO.getIdRole(), "idRole getter must be equal to 1L");
        roleDTO.setIdRole(2L);
        assertEquals(2L, roleDTO.getIdRole(), "idRole setter must be equal to 2L");

        assertEquals(RoleEnum.ADMIN, roleDTO.getRole(), "Role getter must be equal to RoleEnum.ADMIN");
        roleDTO.setRole(RoleEnum.STUDENT);
        assertEquals(RoleEnum.STUDENT, roleDTO.getRole(), "Role setter must be equal to RoleEnum.STUDENT");
    }

    @Test
    void testNotNull() {
        RoleDTO roleDTO = new RoleDTO();
        assertNotNull(roleDTO);
    }
}
