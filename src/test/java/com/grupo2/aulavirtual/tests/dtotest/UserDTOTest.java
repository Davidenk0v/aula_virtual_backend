package com.grupo2.aulavirtual.tests.dtotest;

import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.payload.request.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void testGettersAndSetters() {
        CourseEntity course1 = new CourseEntity();
        CourseEntity course2 = new CourseEntity();

        UserDTO userDTO = UserDTO.builder()
                .idUser("12345")
                .email("test@example.com")
                .lastname("Doe")
                .firstname("John")
                .username("johndoe")
                .password("password")
                .address("123 Main St")
                .courses(List.of(course1, course2))
                .role(List.of("USER", "ADMIN"))
                .urlImg("http://example.com/image.jpg")
                .build();

        assertEquals("12345", userDTO.getIdUser(), "idUser getter must return '12345'");
        userDTO.setIdUser("67890");
        assertEquals("67890", userDTO.getIdUser(), "idUser setter must update the value to '67890'");

        assertEquals("test@example.com", userDTO.getEmail(), "Email getter must return 'test@example.com'");
        userDTO.setEmail("new@example.com");
        assertEquals("new@example.com", userDTO.getEmail(), "Email setter must update the value to 'new@example.com'");

        assertEquals("Doe", userDTO.getLastname(), "Lastname getter must return 'Doe'");
        userDTO.setLastname("Smith");
        assertEquals("Smith", userDTO.getLastname(), "Lastname setter must update the value to 'Smith'");

        assertEquals("John", userDTO.getFirstname(), "Firstname getter must return 'John'");
        userDTO.setFirstname("Jane");
        assertEquals("Jane", userDTO.getFirstname(), "Firstname setter must update the value to 'Jane'");

        assertEquals("johndoe", userDTO.getUsername(), "Username getter must return 'johndoe'");
        userDTO.setUsername("janedoe");
        assertEquals("janedoe", userDTO.getUsername(), "Username setter must update the value to 'janedoe'");

        assertEquals("password", userDTO.getPassword(), "Password getter must return 'password'");
        userDTO.setPassword("newpassword");
        assertEquals("newpassword", userDTO.getPassword(), "Password setter must update the value to 'newpassword'");

        assertEquals("123 Main St", userDTO.getAddress(), "Address getter must return '123 Main St'");
        userDTO.setAddress("456 Elm St");
        assertEquals("456 Elm St", userDTO.getAddress(), "Address setter must update the value to '456 Elm St'");

        assertNotNull(userDTO.getCourses(), "Courses getter must not be null");
        assertEquals(2, userDTO.getCourses().size(), "Courses size must be 2");
        userDTO.setCourses(List.of(course1));
        assertEquals(1, userDTO.getCourses().size(), "Courses setter must update the size to 1");

        assertNotNull(userDTO.getRole(), "Role getter must not be null");
        assertEquals(2, userDTO.getRole().size(), "Role size must be 2");
        userDTO.setRole(List.of("USER"));
        assertEquals(1, userDTO.getRole().size(), "Role setter must update the size to 1");

        assertEquals("http://example.com/image.jpg", userDTO.getUrlImg(), "UrlImg getter must return 'http://example.com/image.jpg'");
        userDTO.setUrlImg("http://example.com/newimage.jpg");
        assertEquals("http://example.com/newimage.jpg", userDTO.getUrlImg(), "UrlImg setter must update the value to 'http://example.com/newimage.jpg'");
    }

    @Test
    void testBuilder() {
        CourseEntity course1 = new CourseEntity();
        CourseEntity course2 = new CourseEntity();

        UserDTO userDTO = UserDTO.builder()
                .idUser("12345")
                .email("test@example.com")
                .lastname("Doe")
                .firstname("John")
                .username("johndoe")
                .password("password")
                .address("123 Main St")
                .courses(List.of(course1, course2))
                .role(List.of("USER", "ADMIN"))
                .urlImg("http://example.com/image.jpg")
                .build();

        assertNotNull(userDTO, "UserDTO object must not be null");
        assertEquals("12345", userDTO.getIdUser(), "idUser must be '12345'");
        assertEquals("test@example.com", userDTO.getEmail(), "Email must be 'test@example.com'");
        assertEquals("Doe", userDTO.getLastname(), "Lastname must be 'Doe'");
        assertEquals("John", userDTO.getFirstname(), "Firstname must be 'John'");
        assertEquals("johndoe", userDTO.getUsername(), "Username must be 'johndoe'");
        assertEquals("password", userDTO.getPassword(), "Password must be 'password'");
        assertEquals("123 Main St", userDTO.getAddress(), "Address must be '123 Main St'");
        assertNotNull(userDTO.getCourses(), "Courses must not be null");
        assertEquals(2, userDTO.getCourses().size(), "Courses size must be 2");
        assertNotNull(userDTO.getRole(), "Role must not be null");
        assertEquals(2, userDTO.getRole().size(), "Role size must be 2");
        assertEquals("http://example.com/image.jpg", userDTO.getUrlImg(), "UrlImg must be 'http://example.com/image.jpg'");
    }

    @Test
    void testNoArgsConstructor() {
        UserDTO userDTO = new UserDTO();
        assertNotNull(userDTO, "UserDTO object must not be null");
    }

    @Test
    void testAllArgsConstructor() {
        CourseEntity course1 = new CourseEntity();
        CourseEntity course2 = new CourseEntity();

        UserDTO userDTO = new UserDTO("12345", "test@example.com", "Doe", "John", "johndoe",
                "password", "123 Main St", List.of(course1, course2),
                List.of("USER", "ADMIN"), "http://example.com/image.jpg");

        assertNotNull(userDTO, "UserDTO object must not be null");
        assertEquals("12345", userDTO.getIdUser(), "idUser must be '12345'");
        assertEquals("test@example.com", userDTO.getEmail(), "Email must be 'test@example.com'");
        assertEquals("Doe", userDTO.getLastname(), "Lastname must be 'Doe'");
        assertEquals("John", userDTO.getFirstname(), "Firstname must be 'John'");
        assertEquals("johndoe", userDTO.getUsername(), "Username must be 'johndoe'");
        assertEquals("password", userDTO.getPassword(), "Password must be 'password'");
        assertEquals("123 Main St", userDTO.getAddress(), "Address must be '123 Main St'");
        assertNotNull(userDTO.getCourses(), "Courses must not be null");
        assertEquals(2, userDTO.getCourses().size(), "Courses size must be 2");
        assertNotNull(userDTO.getRole(), "Role must not be null");
        assertEquals(2, userDTO.getRole().size(), "Role size must be 2");
        assertEquals("http://example.com/image.jpg", userDTO.getUrlImg(), "UrlImg must be 'http://example.com/image.jpg'");
    }
}
