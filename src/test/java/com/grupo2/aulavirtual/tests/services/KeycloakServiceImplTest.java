package com.grupo2.aulavirtual.tests.services;
import com.grupo2.aulavirtual.entities.UserImg;
import com.grupo2.aulavirtual.payload.request.LoginRequestDto;
import com.grupo2.aulavirtual.payload.request.RegisterRequestDto;
import com.grupo2.aulavirtual.payload.request.UserDTO;
import com.grupo2.aulavirtual.repositories.ImageRepository;
import com.grupo2.aulavirtual.services.impl.KeycloakServiceImpl;
import com.grupo2.aulavirtual.util.keycloak.KeycloakProvider;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KeycloakServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private KeycloakServiceImpl keycloakService = new KeycloakServiceImpl();

    @BeforeEach
    void setUp() {
        // Simular comportamiento de KeycloakProvider
        when(KeycloakProvider.getRealmResource().users().list()).thenReturn(Collections.emptyList());
        when(KeycloakProvider.getRealmResource().users().searchByUsername(anyString(), eq(true))).thenReturn(Collections.emptyList());
        when(KeycloakProvider.getRealmResource().users().get(anyString())).thenReturn(null);
        when(KeycloakProvider.getRealmResource().users().search(anyString())).thenReturn(Collections.emptyList());
        when(KeycloakProvider.getRealmResource().roles().get(anyString())).thenReturn(null);
        when(KeycloakProvider.getRealmResource().roles().list()).thenReturn(Collections.emptyList());
    }

    @Test
    void testFindAllUsers_WhenNoUsersFound() {
        // Prueba
        ResponseEntity<?> response = keycloakService.findAllUsers();

        // Verificación
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testFindAllUsers_WhenUsersFound() {
        // Configuración
        when(KeycloakProvider.getRealmResource().users().list()).thenReturn(List.of(new UserRepresentation()));

        // Prueba
        ResponseEntity<?> response = keycloakService.findAllUsers();

        // Verificación
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testLoginUser_WithValidCredentials() {
        // Configuración
        LoginRequestDto loginRequest = new LoginRequestDto("username", "password");

        // Prueba
        ResponseEntity<?> response = keycloakService.loginUser(loginRequest);

        // Verificación
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testLoginUser_WithInvalidCredentials() {
        // Configuración
        LoginRequestDto loginRequest = new LoginRequestDto("invalid_username", "invalid_password");

        // Prueba
        ResponseEntity<?> response = keycloakService.loginUser(loginRequest);

        // Verificación
        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    void testLogoutUser() {
        // Prueba
        ResponseEntity<?> response = keycloakService.logoutUser("user_id");

        // Verificación
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testSearchUserByUsername_WhenNoUserFound() {
        // Prueba
        ResponseEntity<?> response = keycloakService.searchUserByUsername("username");

        // Verificación
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testSearchUserById_WhenNoUserFound() {
        // Prueba
        ResponseEntity<?> response = keycloakService.searchUserById("user_id");

        // Verificación
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testFindUserById() {
        // Configuración
        when(KeycloakProvider.getRealmResource().users().get(anyString())).thenReturn(mock(UserResource.class));
        when(KeycloakProvider.getRealmResource().users().get("existing_user_id")).thenReturn(mock(UserResource.class));

        // Prueba
        UserRepresentation user = keycloakService.findUserById("existing_user_id");

        // Verificación
        assertEquals("existing_user_id", user.getId());
    }

    @Test
    void testFindUserByEmail_WhenNoUserFound() {
        // Prueba
        UserRepresentation user = keycloakService.findUserByEmail("email");

        // Verificación
        assertEquals(null, user);
    }

    @Test
    void testCreateUser_WhenUserAlreadyExists() {
        // Configuración
        RegisterRequestDto userDTO = new RegisterRequestDto();
        when(KeycloakProvider.getUserResource().create(any(UserRepresentation.class))).thenThrow(new RuntimeException("User already exists"));

        // Prueba
        ResponseEntity<?> response = keycloakService.createUser(userDTO);

        // Verificación
        assertEquals(409, response.getStatusCodeValue());
    }

    /*
    @Test
    void testCreateUser_WhenUserCreatedSuccessfully() {
        // Configuración
        RegisterRequestDto userDTO = new RegisterRequestDto();
        Response responseMock = mock(Response.class);
        when(responseMock.getStatus()).thenReturn(201);
        when(responseMock.getLocation()).thenReturn((URI) mock(Response.ResponseBuilder.class));
        when(responseMock.getLocation().getPath()).thenReturn("/users/user_id");
        when(KeycloakProvider.getUserResource().create(any(UserRepresentation.class))).thenReturn(responseMock);
        when(KeycloakProvider.getRealmResource().roles().get(anyString())).thenReturn((RoleResource) mock(RoleRepresentation.class));
        when(KeycloakProvider.getRealmResource().roles().list()).thenReturn(Collections.singletonList(mock(RoleRepresentation.class)));
        when(KeycloakProvider.getRealmResource().users().get(anyString())).thenReturn(mock(UserResource.class));

        // Prueba
        ResponseEntity<?> response = keycloakService.createUser(userDTO);

        // Verificación
        assertEquals(201, response.getStatusCodeValue());
    }


     */
    @Test
    void testDeleteUser() {
        // Prueba
        ResponseEntity<?> response = keycloakService.deleteUser("user_id");

        // Verificación
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateUser() {
        // Configuración
        UserDTO userDTO = new UserDTO();
        UserResource userResourceMock = mock(UserResource.class);
        when(KeycloakProvider.getUserResource().get(anyString())).thenReturn(userResourceMock);

        // Prueba
        ResponseEntity<?> response = keycloakService.updateUser("user_id", userDTO);

        // Verificación
        assertEquals(200, response.getStatusCodeValue());
    }
}
