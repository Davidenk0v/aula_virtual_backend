package com.grupo2.aulavirtual.tests.dtotest;

import com.grupo2.aulavirtual.payload.request.RegisterRequestDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestDtoTest {

    @Test
    void testGettersAndSetters() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto();

        registerRequestDto.setUsername("johndoe");
        assertEquals("johndoe", registerRequestDto.getUsername(), "Username getter must return 'johndoe'");

        registerRequestDto.setFirstname("John");
        assertEquals("John", registerRequestDto.getFirstname(), "Firstname getter must return 'John'");

        registerRequestDto.setLastname("Doe");
        assertEquals("Doe", registerRequestDto.getLastname(), "Lastname getter must return 'Doe'");

        registerRequestDto.setEmail("john.doe@example.com");
        assertEquals("john.doe@example.com", registerRequestDto.getEmail(), "Email getter must return 'john.doe@example.com'");

        registerRequestDto.setRole(List.of("USER", "ADMIN"));
        assertEquals(2, registerRequestDto.getRole().size(), "Role size must be 2");
        assertEquals("USER", registerRequestDto.getRole().get(0), "First role must be 'USER'");
        assertEquals("ADMIN", registerRequestDto.getRole().get(1), "Second role must be 'ADMIN'");

        registerRequestDto.setPassword("password123");
        assertEquals("password123", registerRequestDto.getPassword(), "Password getter must return 'password123'");

        registerRequestDto.setIdKeycloak("keycloak-id");
        assertEquals("keycloak-id", registerRequestDto.getIdKeycloak(), "IdKeycloak getter must return 'keycloak-id'");
    }

    @Test
    void testNoArgsConstructor() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        assertNotNull(registerRequestDto, "RegisterRequestDto object must not be null");
    }

    @Test
    void testAllArgsConstructor() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto(
                "johndoe",
                "John",
                "Doe",
                "john.doe@example.com",
                List.of("USER", "ADMIN"),
                "password123",
                "keycloak-id"
        );

        assertNotNull(registerRequestDto, "RegisterRequestDto object must not be null");
        assertEquals("johndoe", registerRequestDto.getUsername(), "Username must be 'johndoe'");
        assertEquals("John", registerRequestDto.getFirstname(), "Firstname must be 'John'");
        assertEquals("Doe", registerRequestDto.getLastname(), "Lastname must be 'Doe'");
        assertEquals("john.doe@example.com", registerRequestDto.getEmail(), "Email must be 'john.doe@example.com'");
        assertEquals(2, registerRequestDto.getRole().size(), "Role size must be 2");
        assertEquals("USER", registerRequestDto.getRole().get(0), "First role must be 'USER'");
        assertEquals("ADMIN", registerRequestDto.getRole().get(1), "Second role must be 'ADMIN'");
        assertEquals("password123", registerRequestDto.getPassword(), "Password must be 'password123'");
        assertEquals("keycloak-id", registerRequestDto.getIdKeycloak(), "IdKeycloak must be 'keycloak-id'");
    }


}
