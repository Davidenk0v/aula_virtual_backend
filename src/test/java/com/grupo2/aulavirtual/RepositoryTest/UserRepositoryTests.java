package com.grupo2.aulavirtual.RepositoryTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.grupo2.aulavirtual.Entities.UserEntity;
import com.grupo2.aulavirtual.Repository.UserRepository;

@DataJpaTest
public class UserRepositoryTests {

    @MockBean
    private UserRepository userRepository;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userEntity = new UserEntity();
        userEntity.setIdUser(1L);
        userEntity.setEmail("joao@gmail.com");
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
}
