package com.grupo2.aulavirtual.Tests.RepositoryTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.grupo2.aulavirtual.entities.RoleEntity;
import com.grupo2.aulavirtual.entities.enums.RoleEnum;
import com.grupo2.aulavirtual.repositories.RoleRepository;


class RoleRepositoryTests {

    @MockBean
    private RoleRepository roleRepository;

    private RoleEntity role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        role = RoleEntity.builder()
            .idRole(1L)
            .role(RoleEnum.ADMIN)
            .build();
    }

    @Test
    public void saveRoleTest() {
        // Arrange
        when(roleRepository.save(role)).thenReturn(role); // Configuración del comportamiento del mock

        // Act
        RoleEntity savedRole = roleRepository.save(role);

        // Assert
        assertEquals(role, savedRole, "Saved role should match the original role");
    }

    @Test
    public void deleteRoleTest() {
        // Arrange
        when(roleRepository.existsById(role.getIdRole())).thenReturn(true);

        // Act
        boolean roleExists = roleRepository.existsById(role.getIdRole());
        roleRepository.deleteById(role.getIdRole());

        // Assert
        assertTrue(roleExists, "Role should exist before deletion");
        verify(roleRepository, times(1)).existsById(role.getIdRole());
        verify(roleRepository, times(1)).deleteById(role.getIdRole());
    }

    //Test entidad Role
    @Test
    void testGettersAndSetters() {
        assertEquals(1L, role.getIdRole(), "idRole getter must be equal to 1L");
        role.setIdRole(2L);
        assertEquals(2L, role.getIdRole(), "idRole setter must be equal to 2L");

        assertEquals(RoleEnum.ADMIN, role.getRole(), "Role getter must be equal to ADMIN");
        role.setRole(RoleEnum.STUDENT);
        assertEquals(RoleEnum.STUDENT, role.getRole(), "Role setter must be equal to STUDENT");
    }


    @Test
    void testDataAnnotation() {
        RoleEntity roleEntity2 = RoleEntity.builder()
            .idRole(1L)
            .role(RoleEnum.ADMIN)
            .build();

        assertEquals(role.hashCode(), roleEntity2.hashCode(), "Hashcode must be equal");
        assertEquals(role, roleEntity2, "Objects must be equal");

        assertEquals(roleEntity2.toString(), role.toString(), "toString must be equal");
    }

    @Test
    void testBuilder() {
        RoleEntity roleEntity2 = RoleEntity.builder()
            .idRole(1L)
            .role(RoleEnum.ADMIN)
            .build();

        assertEquals(role.hashCode(), roleEntity2.hashCode(), "Hashcode must be equal");
        assertEquals(role, roleEntity2, "Objects must be equal");

        assertEquals(roleEntity2.toString(), role.toString(), "toString must be equal");
    }
}
