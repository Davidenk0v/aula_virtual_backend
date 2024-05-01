package com.grupo2.aulavirtual.tests.services;

import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.SubjectsEntity;
import com.grupo2.aulavirtual.payload.request.SubjectDTO;
import com.grupo2.aulavirtual.payload.response.SubjectsResponseDto;
import com.grupo2.aulavirtual.repositories.CourseRepository;
import com.grupo2.aulavirtual.repositories.SubjectsRepository;
import com.grupo2.aulavirtual.services.SubjectsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SubjectsServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private SubjectsRepository subjectsRepository;

    @InjectMocks
    private SubjectsService subjectsService;

    private SubjectDTO subjectDTO;
    private CourseEntity courseEntity;
    private SubjectsEntity subjectsEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        subjectDTO = SubjectDTO.builder()
                .name("Nuevo tema")
                .description("Descripción del nuevo tema")
                .build();

        courseEntity = new CourseEntity();
        courseEntity.setIdCourse(1L);

        subjectsEntity = new SubjectsEntity();
        subjectsEntity.setIdSubject(1L);
    }

    @Test
    void subjectsListEmpty() {
        // Configurar el comportamiento del repositorio de temas
        when(subjectsRepository.findAll()).thenReturn(Collections.emptyList());

        // Ejecutar el método bajo prueba
        ResponseEntity<?> response = subjectsService.subjectsList();

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No se encontraron temas", response.getBody());
    }

    @Test
    void postSubject() {
        // Configurar el comportamiento del repositorio de cursos
        when(courseRepository.findById(1L)).thenReturn(Optional.of(courseEntity));

        // Configurar el comportamiento del repositorio de temas
        when(subjectsRepository.save(any(SubjectsEntity.class))).thenReturn(subjectsEntity);

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = subjectsService.postSubject(1L, subjectDTO);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey("Tema subido "));
    }

    @Test
    void updateSubject() {
        // Configurar el comportamiento del repositorio de temas
        when(subjectsRepository.existsById(1L)).thenReturn(true);
        when(subjectsRepository.findById(1L)).thenReturn(Optional.of(subjectsEntity));

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = subjectsService.updateSubject(1L, subjectDTO);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey("Se ha modificado correctamente"));
    }

    @Test
    void deleteSubject() {
        // Configurar el comportamiento del repositorio de temas
        when(subjectsRepository.existsById(1L)).thenReturn(true);
        when(subjectsRepository.findById(1L)).thenReturn(Optional.of(subjectsEntity));

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = subjectsService.deleteSubject(1L);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey("Se ha borrado el tema "));
    }

    @Test
    void findSubjectById() {
        // Configurar el comportamiento del repositorio de temas
        when(subjectsRepository.existsById(1L)).thenReturn(true);
        when(subjectsRepository.findById(1L)).thenReturn(Optional.of(subjectsEntity));

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = subjectsService.findSubjectById(1L);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey("Se ha encontrado el tema "));
    }

    @Test
    void subjectIdNotFound() {
        // Configurar el comportamiento del repositorio de temas
        when(subjectsRepository.existsById(2L)).thenReturn(false);

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> responseUpdate = subjectsService.updateSubject(2L, subjectDTO);
        ResponseEntity<HashMap<String, ?>> responseFind = subjectsService.findSubjectById(2L);
        ResponseEntity<HashMap<String, ?>> responseDelete = subjectsService.deleteSubject(2L);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, responseUpdate.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, responseFind.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, responseDelete.getStatusCode());

        // Verificar los mensajes de error
        assertEquals(2L, responseUpdate.getBody().get("No ha encontrado el tema con id: "));
        assertEquals(2L, responseFind.getBody().get("No ha encontrado el tema con id: "));
        assertEquals(2L, responseDelete.getBody().get("No ha encontrado el tema con id: "));
    }

    @Test
    void catchError() {
        // Configurar el comportamiento del repositorio de temas
        when(subjectsRepository.save(any())).thenThrow(new RuntimeException("Error simulado"));
        when(subjectsRepository.existsById(any())).thenThrow(new RuntimeException("Error simulado"));

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> responseUpdate = subjectsService.updateSubject(1L, subjectDTO);
        ResponseEntity<HashMap<String, ?>> responseFind = subjectsService.findSubjectById(1L);
        ResponseEntity<HashMap<String, ?>> responseDelete = subjectsService.deleteSubject(1L);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.INTERNAL_SERVER_ERROR
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseUpdate.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseFind.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseDelete.getStatusCode());

        // Verificar los mensajes de error
        assertEquals("Error simulado", responseUpdate.getBody().get("Error"));
        assertEquals("Error simulado", responseFind.getBody().get("Error"));
        assertEquals("Error simulado", responseDelete.getBody().get("Error"));
    }
}
