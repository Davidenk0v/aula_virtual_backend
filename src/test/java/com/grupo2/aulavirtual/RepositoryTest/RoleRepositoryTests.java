package com.grupo2.aulavirtual.RepositoryTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.grupo2.aulavirtual.Entities.RoleEntity;
import com.grupo2.aulavirtual.Enum.RoleEnum;
import com.grupo2.aulavirtual.Repository.RoleRepository;

@SpringBootTest
class RoleRepositoryTests {

    @MockBean
    private RoleRepository roleRepository;

    private RoleEntity role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        role = new RoleEntity();
        role.setIdRole(1L);
        role.setRole(RoleEnum.ADMIN);
    }

    @Test
    public void saveUserTest() {
        // Arrange
        when(roleRepository.save(role)).thenReturn(role);

        // Act
        RoleEntity savedRole = roleRepository.save(role);

        // Assert
        assertEquals(role, savedRole, "Saved user should match the original user");
    }

    

    @Test
    public void deleteUserTest() {
        // Arrange
        when(roleRepository.existsById(role.getIdRole())).thenReturn(true);

        // Act
        boolean userExists = roleRepository.existsById(role.getIdRole());
        roleRepository.deleteById(role.getIdRole());

        // Assert
        assertTrue(userExists, "User should exist before deletion");
        verify(roleRepository, times(1)).existsById(role.getIdRole());
        verify(roleRepository, times(1)).deleteById(role.getIdRole());
    }

}
