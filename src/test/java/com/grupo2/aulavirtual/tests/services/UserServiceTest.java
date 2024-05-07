package com.grupo2.aulavirtual.tests.services;

import com.grupo2.aulavirtual.mappers.DtoMapper;
import com.grupo2.aulavirtual.entities.RoleEntity;
import com.grupo2.aulavirtual.entities.UserEntity;
import com.grupo2.aulavirtual.entities.enums.RoleEnum;
import com.grupo2.aulavirtual.payload.request.UserDTO;
import com.grupo2.aulavirtual.payload.response.UserResponseDto;
import com.grupo2.aulavirtual.repositories.UserRepository;
import com.grupo2.aulavirtual.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;



    private RoleEntity role;
    private UserEntity user;
    private UserDTO userDTO;
    private UserResponseDto userResponseDto;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        DtoMapper dtoMapper = new DtoMapper();
        role = RoleEntity.builder()
                .role(RoleEnum.ADMIN)
                .build();



        userDTO = UserDTO.builder()
                .idUser(1L)
                .email("test@example.com")
                .lastname("Doe")
                .firstname("John")
                .username("johndoe")
                .address(null)
                .courses(null)
                .build();
        user = dtoMapper.dtoToEntity(userDTO);

        userResponseDto =  dtoMapper.entityToResponseDto(user);
    }

    @Test
    void addUser() {
        // Configura el comportamiento del repositorio
        when(userRepository.save(user)).thenReturn(user);

        // Ejecuta el método y verifica el resultado
        ResponseEntity<HashMap<String, Object>> response = userService.addUser(userDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDTO, Objects.requireNonNull(response.getBody()).get("Guardado"));
    }

    @Test
    void deleteUserNotFound() {
        // Configurar un ID que no exista en el repositorio
        Long id = 2L;

        // Configurar el comportamiento del repositorio para que no exista ningún usuario con el ID dado
        when(userRepository.existsById(id)).thenReturn(false);

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = userService.deleteUser(id);

        // Verificar que se devuelva el mensaje de error apropiado y el código de estado HTTP 500
        assertTrue(Objects.requireNonNull(response.getBody()).containsKey("No se encuntra este usuario con ese id"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void deleteUserError() {
        // Configurar un ID válido
        Long id = 1L;

        // Configurar el comportamiento del repositorio para lanzar una excepción simulada
        when(userRepository.existsById(id)).thenThrow(new RuntimeException("Error simulado"));

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = userService.deleteUser(id);

        // Verificar que se devuelva el mensaje de error apropiado y el código de estado HTTP 500
        assertTrue(Objects.requireNonNull(response.getBody()).containsKey("Error"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    void addUserError() {
        when(userRepository.save(any())).thenThrow(new RuntimeException("Error simulado"));

        ResponseEntity<HashMap<String, Object>> response = userService.addUser(userDTO);

        assertTrue(Objects.requireNonNull(response.getBody()).containsKey("Error"));
        assertEquals(userDTO, response.getBody().get("Error"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void findUserByEmail() {
        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        ResponseEntity<HashMap<String, Object>> response = userService.findUserByEmail(email);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userResponseDto.toString(), Objects.requireNonNull(response.getBody()).get("Guardado").toString());
    }

    @Test
    void findUserByEmailNotFound() {
        String email = "test@example.com";

        // Configura el comportamiento del repositorio para devolver un Optional vacío
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Ejecuta el método bajo prueba
        ResponseEntity<HashMap<String, Object>> response = userService.findUserByEmail(email);

        // Verifica que se devuelva el mensaje de error apropiado
        assertTrue(Objects.requireNonNull(response.getBody()).containsKey("Error"));
        assertEquals("No se encuentra este usuario con ese id", response.getBody().get("Error"));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findUserById() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<HashMap<String, Object>> response = userService.findUserById(userId);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userResponseDto.toString(), response.getBody().get("Guardado").toString());
    }

    @Test
    void findUserByIdNoEncontrado() {
        Long userId = 2L;

        // Configura el comportamiento del repositorio para devolver un Optional vacío
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Ejecuta el método bajo prueba
        ResponseEntity<HashMap<String, Object>> response = userService.findUserById(userId);

        // Verifica que se devuelva el mensaje de error apropiado

        assertEquals("No se encuentra este usuario con ese id", response.getBody().get("Error"));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void userTestError() {
        Long userId = 1L;
        String userEmail = "juan@gmail.com";
        // Configura el comportamiento del repositorio para lanzar una excepción simulada
        when(userRepository.findById(userId)).thenThrow(new RuntimeException("Error simulado"));
        when(userRepository.findByEmail(userEmail)).thenThrow(new RuntimeException("Error simulado"));
        // Ejecuta el método bajo prueba
        ResponseEntity<HashMap<String, Object>> response = userService.findUserById(userId);
        ResponseEntity<HashMap<String, Object>> responseEmail = userService.findUserByEmail(userEmail);
        assertTrue(responseEmail.getBody().containsKey("Error"));
        assertEquals("Error simulado", responseEmail.getBody().get("Error"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEmail.getStatusCode());
        assertTrue(response.getBody().containsKey("Error"));
        assertEquals("Error simulado", response.getBody().get("Error"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }



    @Test
    void updateUser() {
        Long userId = 1L;
        String newUsername = "updatedUsername";
        userDTO.setUsername(newUsername);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<HashMap<String, ?>> response = userService.updateUser(userDTO, userId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newUsername, user.getUsername());
    }

    @Test
    void userList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        assertNotNull(userService.userList().getBody(),"No es nulo");
    }

    @Test
    void deleteUser() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.doNothing().when(userRepository).deleteById(userId);
        ResponseEntity<HashMap<String, ?>> response = userService.deleteUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userId, response.getBody().get("Borrado id"));
    }

}