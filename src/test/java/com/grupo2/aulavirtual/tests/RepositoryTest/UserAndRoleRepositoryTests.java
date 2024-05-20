package com.grupo2.aulavirtual.tests.RepositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.grupo2.aulavirtual.entities.UserEntity;
import com.grupo2.aulavirtual.repositories.UserRepository;

class UserAndRoleRepositoryTests {

    @MockBean
    private UserRepository userRepository;

    private UserEntity userEntity;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userEntity = new UserEntity(1L, "joao@gmail.com", "Joao", "Lima", "Lima", "1", "", null, null);
    }

    @Test
    void saveUserTest() {
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        UserEntity savedUser = userRepository.save(userEntity);

        assertEquals(userEntity, savedUser);
    }

    @Test
    void deleteUserTest() {
        // Arrange
        when(userRepository.existsById(userEntity.getIdUser())).thenReturn(true);

        // Act
        boolean userExists = userRepository.existsById(userEntity.getIdUser());
        userRepository.deleteById(userEntity.getIdUser());

        // Assert
        assertTrue(userExists, "User should exist before deletion");
        verify(userRepository, times(1)).existsById(userEntity.getIdUser());
        verify(userRepository, times(1)).deleteById(userEntity.getIdUser());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, userEntity.getIdUser(), "idUser getter must be equal to 1L");
        userEntity.setIdUser(2L);
        assertEquals(2L, userEntity.getIdUser(), "idUser setter must be equal to 2L");

        assertEquals("joao@gmail.com", userEntity.getEmail(), "Email getter must be equal to \"joao@gmail.com\"");
        userEntity.setEmail("pepito@gmail.com");
        assertEquals("pepito@gmail.com", userEntity.getEmail(), "Email setter must be equal to \"pepito@gmail.com\"");

        assertEquals("Lima", userEntity.getFirstname(), "Firstname getter must be equal to \"Joao\"");
        userEntity.setFirstname("Manuel");
        assertEquals("Manuel", userEntity.getFirstname(), "Firstname setter must be equal to \"Manuel\"");

        assertEquals("Joao", userEntity.getLastname(), "Lastname getter must be equal to \"Lima\"");
        userEntity.setLastname("Ford");
        assertEquals("Ford", userEntity.getLastname(), "Lastname setter must be equal to \"Ford\"");

    }



    @Test
    void testDataAnnotation() {
        // Test Equals and HashCode
        UserEntity userEntity2 = new UserEntity(1L, "joao@gmail.com", "Joao", "Lima", "Lima", "contraseña", "", null,
                null);
        assertEquals(userEntity.hashCode(), userEntity2.hashCode(), "Hashcode must be equal");
        assertEquals(userEntity, userEntity2, "Objects must be equal");

        // Test toString
        assertEquals(userEntity2.toString(), userEntity.toString(), "toString must be equal");
    }

    @Test
    void testBuilder() {
        // Test Equals and HashCode
        UserEntity userEntity2 = UserEntity.builder().idUser(1L).email("joao@gmail.com").firstname("Lima")
                .lastname("Joao").username("Lima").idKeycloak("1").urlImg("")
                .address(null).courses(null).build();
        assertEquals(userEntity.hashCode(), userEntity2.hashCode(), "Hashcode must be equal");
        assertEquals(userEntity, userEntity2, "Objects must be equal");

        // Test toString
        assertEquals(userEntity2.toString(), userEntity.toString(), "toString must be equal");
    }
}
