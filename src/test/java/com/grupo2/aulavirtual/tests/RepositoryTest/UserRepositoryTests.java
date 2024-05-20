package com.grupo2.aulavirtual.tests.RepositoryTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.grupo2.aulavirtual.entities.UserEntity;
import com.grupo2.aulavirtual.repositories.UserRepository;

class UserRepositoryTests {

    @MockBean
    private UserRepository userRepository;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userEntity = new UserEntity();
        userEntity.setIdUser(1L);
        userEntity.setEmail("joao@gmail.com");
        userEntity.setFirstname("Joao");
        userEntity.setLastname("Lima");
        userEntity.setIdKeycloak("Lima");
    }

    @Test
    public void saveUserTest() {
        // Arrange
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        // Act
        UserEntity savedUser = userRepository.save(userEntity);

        // Assert
        assertEquals(userEntity, savedUser, "Saved user should match the original user");
    }

    

    @Test
    public void deleteUserTest() {
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

}
