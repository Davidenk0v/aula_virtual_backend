package com.grupo2.aulavirtual.tests.services;

import com.grupo2.aulavirtual.config.mappers.DtoMapper;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.RoleEntity;
import com.grupo2.aulavirtual.entities.UserEntity;
import com.grupo2.aulavirtual.entities.enums.RoleEnum;
import com.grupo2.aulavirtual.payload.request.CourseDTO;
import com.grupo2.aulavirtual.payload.request.UserDTO;
import com.grupo2.aulavirtual.payload.response.UserResponseDto;
import com.grupo2.aulavirtual.repositories.CourseRepository;
import com.grupo2.aulavirtual.repositories.UserRepository;
import com.grupo2.aulavirtual.services.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

class CoursesServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CourseService coursesService;

    private CourseDTO courseDTO;
    private CourseEntity courseEntity;
    private UserEntity user;
    private UserDTO userDTO;
    private UserResponseDto userResponseDto;
    private DtoMapper dtoMapper = new DtoMapper();
    private RoleEntity role;
    @BeforeEach
    void setUp() {
         MockitoAnnotations.openMocks(this);

         courseDTO = CourseDTO.builder()
                .idCourse(1L) // Este campo puede variar dependiendo de cómo se use el DTO
                .name("Curso de ejemplo")
                .description("Descripción del curso")
                .startDate(Date.valueOf("2024-05-01")) // Ejemplo de fecha de inicio
                .finishDate(Date.valueOf("2024-06-30")) // Ejemplo de fecha de finalización
                .pago(BigDecimal.valueOf(100)) // Ejemplo de monto de pago
                // Añadir otras configuraciones según sea necesario
                .build();
         courseEntity = dtoMapper.dtoToEntity(courseDTO);

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
                .role(role)
                .build();
        user = dtoMapper.dtoToEntity(userDTO);

        userResponseDto =  dtoMapper.entityToResponseDto(user);
    }
    @Test
    void courseListEmpty() {
        // Configurar el comportamiento del repositorio de cursos
        when(courseRepository.findAll()).thenReturn(Collections.emptyList());

        // Ejecutar el método bajo prueba
        ResponseEntity<?> response = coursesService.courseList();

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No se encontraron cursos", response.getBody());
    }

    @Test
    void postCourse() {
        // Configurar los datos de entrada
        Long userId = 1L;

        // Configurar el comportamiento del repositorio de usuarios
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Configurar el comportamiento del repositorio de cursos
        when(courseRepository.save(any(CourseEntity.class))).thenAnswer(invocation -> {
            CourseEntity courseEntity = invocation.getArgument(0);
            return courseEntity;
        });

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = coursesService.postCourse(userId, courseDTO);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey("Curso subido "));
    }




    @Test
    void updateCourse() {
        // Configurar los datos de entrada
        Long courseId = 1L;
        courseDTO = CourseDTO.builder()
                .name("Nuevo nombre")
                .description("Nueva descripción")
                .build();

        // Configurar el comportamiento del repositorio de cursos
        when(courseRepository.existsById(courseId)).thenReturn(true);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new CourseEntity()));

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = coursesService.updateCourse(courseId, courseDTO);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey("Se ha modificado correctamente"));
    }

    @Test
    void findCourseById() {
        // Configurar los datos de entrada
        Long courseId = 1L;

        // Configurar el comportamiento del repositorio de cursos
        when(courseRepository.existsById(courseId)).thenReturn(true);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new CourseEntity()));

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = coursesService.findCourseById(courseId);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey("Id encontrado "));
    }


    @Test
    void idNotFound() {
        Long courseId = 2L;
        when(courseRepository.existsById(courseId)).thenReturn(false);

        ResponseEntity<HashMap<String, ?>> responseUpdate = coursesService.updateCourse(courseId,courseDTO);
        ResponseEntity<HashMap<String, ?>> responseFind = coursesService.findCourseById(courseId);
        ResponseEntity<HashMap<String, ?>> responseDelete = coursesService.deleteCourse(courseId);

        assertEquals(courseId, responseUpdate.getBody().get("No ha encontrado el curso con id: "));
        assertEquals(HttpStatus.NOT_FOUND, responseUpdate.getStatusCode());
        assertEquals(courseId, responseFind.getBody().get("No ha encontrado el curso con id: "));
        assertEquals(HttpStatus.NOT_FOUND, responseFind.getStatusCode());
        assertEquals(courseId, responseDelete.getBody().get("No ha encontrado el curso con id: "));
        assertEquals(HttpStatus.NOT_FOUND, responseDelete.getStatusCode());
    }

    @Test
    void catchError() {
        Long courseId = 1L;
        when(courseRepository.save(any())).thenThrow(new RuntimeException("Error simulado"));
        when(courseRepository.existsById(any())).thenThrow(new RuntimeException("Error simulado"));
        ResponseEntity<HashMap<String, ?>> responseUpdate = coursesService.updateCourse(courseId,courseDTO);
        ResponseEntity<HashMap<String, ?>> responseFind = coursesService.findCourseById(courseId);
        ResponseEntity<HashMap<String, ?>> responseDelete = coursesService.deleteCourse(courseId);

        assertTrue(responseUpdate.getBody().containsKey("Error"));
        assertEquals("Error simulado", responseUpdate.getBody().get("Error"));
        assertEquals("Error simulado", responseFind.getBody().get("Error"));
        assertEquals("Error simulado", responseDelete.getBody().get("Error"));
    }
}