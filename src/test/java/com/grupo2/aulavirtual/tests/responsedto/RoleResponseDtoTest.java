package com.grupo2.aulavirtual.tests.responsedto;

import com.grupo2.aulavirtual.Entities.RoleEntity;
import com.grupo2.aulavirtual.Enum.RoleEnum;
import com.grupo2.aulavirtual.Payload.Response.RoleResponseDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleResponseDtoTest {

    @Test
    void testGettersAndSetters() {
        RoleResponseDto roleResponseDto = RoleResponseDto.builder()
                .idRole(1L)
                .role(RoleEnum.ADMIN)
                .user(null)
                .build();

        assertEquals(1L, roleResponseDto.getIdRole(), "idRole getter must be equal to 1L");
        roleResponseDto.setIdRole(2L);
        assertEquals(2L, roleResponseDto.getIdRole(), "idRole setter must be equal to 2L");

        assertEquals(RoleEnum.ADMIN, roleResponseDto.getRole(), "Role getter must be equal to RoleEnum.ADMIN");
        roleResponseDto.setRole(RoleEnum.STUDENT);
        assertEquals(RoleEnum.STUDENT, roleResponseDto.getRole(), "Role setter must be equal to RoleEnum.STUDENT");
    }

    @Test
    void testNotNull() {
        RoleResponseDto roleResponseDto = new RoleResponseDto();
        assertNotNull(roleResponseDto);
    }
}