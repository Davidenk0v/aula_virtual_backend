package com.grupo2.aulavirtual.tests.services;

import com.grupo2.aulavirtual.Config.Mappers.DtoMapper;
import com.grupo2.aulavirtual.Entities.LessonsEntity;
import com.grupo2.aulavirtual.Entities.SubjectsEntity;
import com.grupo2.aulavirtual.Payload.Request.LessonsDTO;
import com.grupo2.aulavirtual.Repository.LessonsRepository;
import com.grupo2.aulavirtual.Repository.SubjectsRepository;
import com.grupo2.aulavirtual.Services.LessonsService;
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

class LessonsServiceTest {
    @Mock
    private SubjectsRepository subjectsRepository;

    @Mock
    private LessonsRepository lessonsRepository;

    @InjectMocks
    private LessonsService lessonsService;

    private LessonsDTO lessonsDTO;
    private SubjectsEntity subjectsEntity;
    private LessonsEntity lessonsEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        DtoMapper mapper = new DtoMapper();
        lessonsDTO = LessonsDTO.builder()
                .idLesson(1L)
                .name("Nueva lección")
                .description("Descripción de la nueva lección")
                .build();

        subjectsEntity = new SubjectsEntity();
        subjectsEntity.setIdSubject(1L);

        lessonsEntity = mapper.dtoToEntity(lessonsDTO);
    }

    @Test
    void lessonsListEmpty() {
        // Configurar el comportamiento del repositorio de lecciones
        when(lessonsRepository.findAll()).thenReturn(Collections.emptyList());

        // Ejecutar el método bajo prueba
        ResponseEntity<?> response = lessonsService.lessonsList();

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No se encontraron lecciones", response.getBody());
    }

    @Test
    void postLessons() {
        // Configurar el comportamiento del repositorio de temas
        when(subjectsRepository.findById(1L)).thenReturn(Optional.of(subjectsEntity));

        // Configurar el comportamiento del repositorio de lecciones
        when(lessonsRepository.save(any(LessonsEntity.class))).thenReturn(lessonsEntity);

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = lessonsService.postLessons(1L, lessonsDTO);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey("Tema subido "));
    }
    /*
    @Test
    void updateLesson() {
        // Configurar el comportamiento del repositorio de lecciones
        when(lessonsRepository.existsById(1L)).thenReturn(true);
        when(lessonsRepository.findById(1L)).thenReturn(Optional.of(lessonsEntity));

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = lessonsService.updateLesson(1L, lessonsDTO);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey("Se ha modificado correctamente"));
    }

    @Test
    void deleteLesson() {
        // Configurar el comportamiento del repositorio de lecciones
        when(lessonsRepository.existsById(1L)).thenReturn(true);
        when(lessonsRepository.findById(1L)).thenReturn(Optional.of(lessonsEntity));

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = lessonsService.deleteLesson(1L);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey("Se ha borrado el tema "));
    }

    @Test
    void findLessonsById() {
        // Configurar el comportamiento del repositorio de lecciones
        when(lessonsRepository.existsById(1L)).thenReturn(true);
        when(lessonsRepository.findById(1L)).thenReturn(Optional.of(lessonsEntity));

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = lessonsService.findLessonsById(1L);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey("Error"));
    }

    @Test
    void lessonIdNotFound() {
        // Configurar el comportamiento del repositorio de lecciones
        when(lessonsRepository.existsById(2L)).thenReturn(false);

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> responseUpdate = lessonsService.updateLesson(2L, lessonsDTO);
        ResponseEntity<HashMap<String, ?>> responseFind = lessonsService.findLessonsById(2L);
        ResponseEntity<HashMap<String, ?>> responseDelete = lessonsService.deleteLesson(2L);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, responseUpdate.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, responseFind.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, responseDelete.getStatusCode());

        // Verificar los mensajes de error
        assertEquals(2L, responseUpdate.getBody().get("No ha encontrado el curso con id: "));
        assertEquals(2L, responseFind.getBody().get("No ha encontrado el curso con id: "));
        assertEquals(2L, responseDelete.getBody().get("No ha encontrado el curso con id: "));
    }
    */

    @Test
    void catchError() {
        // Configurar el comportamiento del repositorio de lecciones
        when(lessonsRepository.save(any())).thenThrow(new RuntimeException("Error simulado"));
        when(lessonsRepository.existsById(any())).thenThrow(new RuntimeException("Error simulado"));

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> responseUpdate = lessonsService.updateLesson(1L, lessonsDTO);
        ResponseEntity<HashMap<String, ?>> responseFind = lessonsService.findLessonsById(1L);
        ResponseEntity<HashMap<String, ?>> responseDelete = lessonsService.deleteLesson(1L);

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
