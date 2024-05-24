package com.grupo2.aulavirtual.tests.services;

import com.grupo2.aulavirtual.entities.MeetingEntity;
import com.grupo2.aulavirtual.entities.ProgressEntity;
import com.grupo2.aulavirtual.repositories.MeetingRepository;
import com.grupo2.aulavirtual.repositories.ProgressRepository;
import com.grupo2.aulavirtual.services.MeetingService;
import com.grupo2.aulavirtual.services.ProgressService;
import com.grupo2.aulavirtual.services.impl.MeetingServiceImpl;
import com.grupo2.aulavirtual.services.impl.ProgressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProgressServerTest {

    @Mock
    private ProgressRepository repository;

    @InjectMocks
    private ProgressService progressService = new ProgressServiceImpl();


    private ProgressEntity progressEntity;
    private static final String NOT_FOUND = "No encontrado";
    private static final String SAVE = "data";
    private static final String ERROR = "error";
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        progressEntity = ProgressEntity.builder()
                .id(1L)
                .idKeyCloak("12345-abcde")
                .idCourse(101L)
                .points(85.5f)
                .progress(75.0f)
                .build();
    }
    @Test
    void postProgress() {
        when(repository.save(any(ProgressEntity.class))).thenReturn(progressEntity);

        ResponseEntity<HashMap<String, ?>> response = (ResponseEntity<HashMap<String, ?>>) progressService.postProgress("12345-abcde", 101L);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().containsKey(SAVE));
    }

    @Test
    void sumValue() {
        when(repository.findByIdCourseAndIdKeyCloak(101L, "12345-abcde")).thenReturn(Optional.of(progressEntity));
        when(repository.save(any(ProgressEntity.class))).thenReturn(progressEntity);

        ResponseEntity<HashMap<String, ?>> response = (ResponseEntity<HashMap<String, ?>>) progressService.sumValue("12345-abcde", 101L, 10.0f);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().containsKey(SAVE));
    }

    @Test
    void getProgress() {
        when(repository.findByIdCourseAndIdKeyCloak(101L, "12345-abcde")).thenReturn(Optional.of(progressEntity));

        ResponseEntity<HashMap<String, ?>> response = (ResponseEntity<HashMap<String, ?>>) progressService.getProgress("12345-abcde", 101L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().containsKey(SAVE));
    }

    @Test
    void deleteProgress() {
        when(repository.findByIdCourseAndIdKeyCloak(101L, "12345-abcde")).thenReturn(Optional.of(progressEntity));

        ResponseEntity<HashMap<String, ?>> response = (ResponseEntity<HashMap<String, ?>>) progressService.deleteProgress("12345-abcde", 101L);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().containsKey(SAVE));
    }

    @Test
    void progressNotFound() {
        when(repository.findByIdCourseAndIdKeyCloak(202L, "67890-fghij")).thenReturn(Optional.empty());

        ResponseEntity<HashMap<String, ?>> response = (ResponseEntity<HashMap<String, ?>>) progressService.getProgress("67890-fghij", 202L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void catchError() {
        when(repository.save(any())).thenThrow(new RuntimeException("Error simulado"));
        when(repository.findByIdCourseAndIdKeyCloak(any(), any())).thenThrow(new RuntimeException("Error simulado"));

        ResponseEntity<HashMap<String, ?>> responsePost = (ResponseEntity<HashMap<String, ?>>) progressService.postProgress("12345-abcde", 101L);
        ResponseEntity<HashMap<String, ?>> responseSum = (ResponseEntity<HashMap<String, ?>>) progressService.sumValue("12345-abcde", 101L, 10.0f);
        ResponseEntity<HashMap<String, ?>> responseGet = (ResponseEntity<HashMap<String, ?>>) progressService.getProgress("12345-abcde", 101L);
        ResponseEntity<HashMap<String, ?>> responseDelete = (ResponseEntity<HashMap<String, ?>>) progressService.deleteProgress("12345-abcde", 101L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responsePost.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseSum.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseGet.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseDelete.getStatusCode());

        assertEquals("Error simulado", responsePost.getBody().get(ERROR));
        assertEquals("Error simulado", responseSum.getBody().get(ERROR));
        assertEquals("Error simulado", responseGet.getBody().get(ERROR));
        assertEquals("Error simulado", responseDelete.getBody().get(ERROR));
    }
}