package com.grupo2.aulavirtual.tests.services;

import com.grupo2.aulavirtual.entities.LessonsEntity;
import com.grupo2.aulavirtual.entities.MeetingEntity;
import com.grupo2.aulavirtual.repositories.MeetingRepository;
import com.grupo2.aulavirtual.services.MeetingService;
import com.grupo2.aulavirtual.services.impl.MeetingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MeetingServiceTest {
    @Mock
    private MeetingRepository repository;

    @InjectMocks
    private MeetingService meetingService = new MeetingServiceImpl();


    private MeetingEntity meeting;
    private static final String NOT_FOUND = "No encontrado";
    private static final String SAVE = "data";
    private static final String ERROR = "error";
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        meeting = MeetingEntity.builder()
                .idMeeting(1L)
                .nameTeacher("Joao")
                .numberMeeting(123456789)
                .password("1234")
                .build();
    }

    @Test
    void repositoryMeetingEmpty() {
        // Configurar el comportamiento del repositorio de reuniones
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // Ejecutar el método bajo prueba
        ResponseEntity<?> response = meetingService.repositoryMeeting();

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(NOT_FOUND, response.getBody());
    }

    @Test
    void repositoryMeeting() {
        // Configurar el comportamiento del repositorio de reuniones
        when(repository.findAll()).thenReturn(List.of(meeting));

        // Ejecutar el método bajo prueba
        ResponseEntity<?> response = meetingService.repositoryMeeting();

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(meeting), response.getBody());
    }

    @Test
    void postMeeting() {
        // Configurar el comportamiento del repositorio de reuniones
        when(repository.save(any(MeetingEntity.class))).thenReturn(meeting);

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = meetingService.postMeeting(meeting);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey(SAVE));
        assertEquals(meeting, response.getBody().get(SAVE));
    }

    @Test
    void deleteMeeting() {
        // Configurar el comportamiento del repositorio de reuniones
        when(repository.existsById(1L)).thenReturn(true);

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = meetingService.deleteMeeting(1L);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey(SAVE));
        assertEquals(1L, response.getBody().get(SAVE));
    }

    @Test
    void deleteMeetingNotFound() {
        // Configurar el comportamiento del repositorio de reuniones
        when(repository.existsById(1L)).thenReturn(false);

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = meetingService.deleteMeeting(1L);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey(NOT_FOUND));
        assertEquals(1L, response.getBody().get(NOT_FOUND));
    }

    @Test
    void catchError() {
        // Configurar el comportamiento del repositorio de reuniones para lanzar una excepción
        when(repository.save(any())).thenThrow(new RuntimeException("Error simulado"));
        when(repository.existsById(any())).thenThrow(new RuntimeException("Error simulado"));

        // Ejecutar el método bajo prueba
        try {
            meetingService.postMeeting(meeting);
        } catch (RuntimeException e) {
            assertEquals("Error simulado", e.getMessage());
        }

        try {
            meetingService.deleteMeeting(1L);
        } catch (RuntimeException e) {
            assertEquals("Error simulado", e.getMessage());
        }
    }
}