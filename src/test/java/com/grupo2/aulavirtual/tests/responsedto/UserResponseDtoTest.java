package com.grupo2.aulavirtual.tests.responsedto;

import com.grupo2.aulavirtual.payload.response.UserResponseDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseDtoTest {
    @Test
    void testGettersAndSetters() {
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .idUser(1L)
                .email("test@example.com")
                .lastname("Doe")
                .firstname("John")
                .username("johndoe")
                .address(null)
                .courses(null)
                .role(null)
                .build();

        assertEquals(1L, userResponseDto.getIdUser(), "idUser getter must be equal to 1L");
        userResponseDto.setIdUser(2L);
        assertEquals(2L, userResponseDto.getIdUser(), "idUser setter must be equal to 2L");

        assertEquals("test@example.com", userResponseDto.getEmail(),
                "Email getter must be equal to \"test@example.com\"");
        userResponseDto.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", userResponseDto.getEmail(),
                "Email setter must be equal to \"newemail@example.com\"");

        assertEquals("Doe", userResponseDto.getLastname(), "Lastname getter must be equal to \"Doe\"");
        userResponseDto.setLastname("Smith");
        assertEquals("Smith", userResponseDto.getLastname(), "Lastname setter must be equal to \"Smith\"");

        assertEquals("John", userResponseDto.getFirstname(), "Firstname getter must be equal to \"John\"");
        userResponseDto.setFirstname("Jane");
        assertEquals("Jane", userResponseDto.getFirstname(), "Firstname setter must be equal to \"Jane\"");

        assertEquals("johndoe", userResponseDto.getUsername(), "Username getter must be equal to \"johndoe\"");
        userResponseDto.setUsername("janedoe");
        assertEquals("janedoe", userResponseDto.getUsername(), "Username setter must be equal to \"janedoe\"");

    }

    @Test
    void testNotNull() {
        UserResponseDto userResponseDto = new UserResponseDto();
        assertNotNull(userResponseDto);
    }
}