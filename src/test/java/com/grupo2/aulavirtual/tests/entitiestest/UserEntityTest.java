package com.grupo2.aulavirtual.tests.entitiestest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void testGettersAndSetters() {
        UserEntity userDTO = UserEntity.builder()
                .idUser(1L)
                .email("test@example.com")
                .lastname("Doe")
                .firstname("John")
                .username("johndoe")
                .address(null)
                .courses(null)
                .build();

        assertEquals(1L, userDTO.getIdUser(), "idUser getter must be equal to 1L");
        userDTO.setIdUser(2L);
        assertEquals(2L, userDTO.getIdUser(), "idUser setter must be equal to 2L");

        assertEquals("test@example.com", userDTO.getEmail(), "Email getter must be equal to \"test@example.com\"");
        userDTO.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", userDTO.getEmail(), "Email setter must be equal to \"newemail@example.com\"");

        assertEquals("Doe", userDTO.getLastname(), "Lastname getter must be equal to \"Doe\"");
        userDTO.setLastname("Smith");
        assertEquals("Smith", userDTO.getLastname(), "Lastname setter must be equal to \"Smith\"");

        assertEquals("John", userDTO.getFirstname(), "Firstname getter must be equal to \"John\"");
        userDTO.setFirstname("Jane");
        assertEquals("Jane", userDTO.getFirstname(), "Firstname setter must be equal to \"Jane\"");

        assertEquals("johndoe", userDTO.getUsername(), "Username getter must be equal to \"johndoe\"");
        userDTO.setUsername("janedoe");
        assertEquals("janedoe", userDTO.getUsername(), "Username setter must be equal to \"janedoe\"");

    }

    @Test
    void testNotNull() {
        UserEntity userDTO = new UserEntity();
        assertNotNull(userDTO);
    }
}