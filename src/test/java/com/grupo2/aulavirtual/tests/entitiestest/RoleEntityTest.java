package com.grupo2.aulavirtual.tests.entitiestest;

import com.grupo2.aulavirtual.entities.RoleEntity;
import com.grupo2.aulavirtual.entities.enums.RoleEnum;
import com.grupo2.aulavirtual.payload.request.RoleDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleEntityTest {

    @Test
    void testGettersAndSetters() {
        RoleEntity roleEntity = RoleEntity.builder()
                .idRole(1L)
                .role(RoleEnum.ADMIN)
                .user(null)
                .build();

        assertEquals(1L, roleEntity.getIdRole(), "idRole getter must be equal to 1L");
        roleEntity.setIdRole(2L);
        assertEquals(2L, roleEntity.getIdRole(), "idRole setter must be equal to 2L");

        assertEquals(RoleEnum.ADMIN, roleEntity.getRole(), "Role getter must be equal to RoleEnum.ADMIN");
        roleEntity.setRole(RoleEnum.STUDENT);
        assertEquals(RoleEnum.STUDENT, roleEntity.getRole(), "Role setter must be equal to RoleEnum.STUDENT");
    }

    @Test
    void testNotNull() {
        RoleEntity roleEntity = new RoleEntity();
        assertNotNull(roleEntity);
    }
}