package com.grupo2.aulavirtual.tests.responsedto;

import com.grupo2.aulavirtual.payload.response.CourseResponseDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class userResponseDto {
    @Test
    void testGettersAndSetters() {
        List<CourseResponseDto> courses = List.of(
                CourseResponseDto.builder().idCourse(1L).name("Java Programming").build(),
                CourseResponseDto.builder().idCourse(2L).name("Python Programming").build()
        );

        com.grupo2.aulavirtual.payload.response.UserResponseDto userResponseDto = com.grupo2.aulavirtual.payload.response.UserResponseDto.builder()
                .idUser(1L)
                .email("user@example.com")
                .lastname("Doe")
                .firstname("John")
                .username("johndoe")
                .address("123 Main St")
                .courses(courses)
                .role(List.of("ROLE_USER"))
                .urlImg("http://example.com/image.jpg")
                .build();

        assertEquals(1L, userResponseDto.getIdUser(), "idUser getter must be equal to 1L");
        userResponseDto.setIdUser(2L);
        assertEquals(2L, userResponseDto.getIdUser(), "idUser setter must be equal to 2L");

        assertEquals("user@example.com", userResponseDto.getEmail(), "Email getter must be equal to 'user@example.com'");
        userResponseDto.setEmail("admin@example.com");
        assertEquals("admin@example.com", userResponseDto.getEmail(), "Email setter must be equal to 'admin@example.com'");

        assertEquals("Doe", userResponseDto.getLastname(), "Lastname getter must be equal to 'Doe'");
        userResponseDto.setLastname("Smith");
        assertEquals("Smith", userResponseDto.getLastname(), "Lastname setter must be equal to 'Smith'");

        assertEquals("John", userResponseDto.getFirstname(), "Firstname getter must be equal to 'John'");
        userResponseDto.setFirstname("Jane");
        assertEquals("Jane", userResponseDto.getFirstname(), "Firstname setter must be equal to 'Jane'");

        assertEquals("johndoe", userResponseDto.getUsername(), "Username getter must be equal to 'johndoe'");
        userResponseDto.setUsername("janedoe");
        assertEquals("janedoe", userResponseDto.getUsername(), "Username setter must be equal to 'janedoe'");

        assertEquals("123 Main St", userResponseDto.getAddress(), "Address getter must be equal to '123 Main St'");
        userResponseDto.setAddress("456 Elm St");
        assertEquals("456 Elm St", userResponseDto.getAddress(), "Address setter must be equal to '456 Elm St'");

        assertEquals(courses, userResponseDto.getCourses(), "Courses getter must return the correct list of courses");
        userResponseDto.setCourses(null);
        assertNull(userResponseDto.getCourses(), "Courses setter must be able to set the value to null");

        assertEquals(List.of("ROLE_USER"), userResponseDto.getRole(), "Role getter must return the correct list of roles");
        userResponseDto.setRole(List.of("ROLE_ADMIN"));
        assertEquals(List.of("ROLE_ADMIN"), userResponseDto.getRole(), "Role setter must be able to set the correct value");

        assertEquals("http://example.com/image.jpg", userResponseDto.getUrlImg(), "UrlImg getter must be equal to 'http://example.com/image.jpg'");
        userResponseDto.setUrlImg("http://example.com/newimage.jpg");
        assertEquals("http://example.com/newimage.jpg", userResponseDto.getUrlImg(), "UrlImg setter must be equal to 'http://example.com/newimage.jpg'");
    }

    @Test
    void testNotNull() {
        com.grupo2.aulavirtual.payload.response.UserResponseDto userResponseDto = new com.grupo2.aulavirtual.payload.response.UserResponseDto();
        assertNotNull(userResponseDto);
    }
}
