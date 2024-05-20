package com.grupo2.aulavirtual.tests.services;

import com.grupo2.aulavirtual.entities.CommentEntity;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.UserEntity;
import com.grupo2.aulavirtual.mappers.DtoMapper;
import com.grupo2.aulavirtual.payload.request.CommentDTO;
import com.grupo2.aulavirtual.payload.request.CourseDTO;
import com.grupo2.aulavirtual.payload.request.UserDTO;
import com.grupo2.aulavirtual.payload.response.CommentResponseDto;
import com.grupo2.aulavirtual.payload.response.CourseResponseDto;
import com.grupo2.aulavirtual.payload.response.UserResponseDto;
import com.grupo2.aulavirtual.repositories.CommentRepository;
import com.grupo2.aulavirtual.repositories.CourseRepository;
import com.grupo2.aulavirtual.repositories.UserRepository;
import com.grupo2.aulavirtual.services.CommentService;

import io.micrometer.core.ipc.http.HttpSender;
import io.micrometer.core.ipc.http.HttpSender.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    private CommentDTO commentDTO;
    private CommentResponseDto commentResponseDto;
    private CommentEntity commentEntity;
    private CourseDTO courseDTO;
    private CourseResponseDto courseResponseDto;
    private CourseEntity courseEntity;
    private UserDTO userDTO;
    private UserResponseDto userResponseDto;
    private UserEntity userEntity;
    private DtoMapper dtoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        commentDTO = CommentDTO.builder()
                .idComment(1)
                .course(null)
                .user(null)
                .text("DTO de prueba")
                .date(Date.valueOf("2024-09-01"))
                .build();

        courseDTO = CourseDTO.builder()
                .idCourse(1L)
                .name("Curso de ejemplo")
                .description("Descripción del curso")
                .startDate(Date.valueOf("2024-05-01"))
                .finishDate(Date.valueOf("2024-06-30"))
                .price(BigDecimal.valueOf(100))
                .build();

        userDTO = UserDTO.builder()
                .idUser(1L)
                .email("test@example.com")
                .lastname("Doe")
                .firstname("John")
                .username("johndoe")
                .address(null)
                .courses(null)
                .role(null)
                .build();
    }

    @Test
    void commentListEmpty() {
        when(commentRepository.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = commentService.commentList();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No se encontraron comentarios", response.getBody());
    }

    @Test
    void postComment() {
        // Configurar los datos de entrada
        String userId = "User";
        Long courseId = 1L;
        // Configurar el comportamiento del repositorio de usuarios
        when(userRepository.findByIdKeycloak(userId)).thenReturn(Optional.of(userEntity));
        // Configurar el comportamiento del repositorio de course
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
        // Configurar el comportamiento del repositorio de cursos
        when(commentRepository.save(any(CommentEntity.class))).thenAnswer(invocation -> {
            CommentEntity commentEntity = invocation.getArgument(0);
            return commentEntity;
        });

        // Ejecutar el método bajo prueba
        ResponseEntity<?> response = commentService.postComment(userId, courseId, commentDTO);

        // Verificar que se recibe una respuesta con el código de estado
        // HttpStatus.CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().equals(commentDTO));
    }

    @Test
    void updateComment() {
        int commentId = 1;
        commentDTO = commentDTO.builder()
                .text("Prueba")
                .date(Date.valueOf("2024-12-01"))
                .build();

        when(commentRepository.existsById(commentId)).thenReturn(true);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(new CommentEntity()));
        ResponseEntity<HashMap<String, ?>> response = (ResponseEntity<HashMap<String, ?>>) commentService.updateComment(commentId, commentDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().containsKey("Se ha modificado correctamente"));
    }

    @Test
    void findCommentById() {
        int commentId = 1;
        when(commentRepository.existsById(commentId)).thenReturn(true);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(new CommentEntity()));
        ResponseEntity<?> response = commentService.findCommentById(commentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().equals(commentDTO));
    }

    @Test
    void idNotFound() {
        int commentId = 1;
        when(commentRepository.existsById(commentId)).thenReturn(false);
        ResponseEntity<HashMap<String, ?>> responseUpdate = (ResponseEntity<HashMap<String, ?>>) commentService.updateComment(commentId,commentDTO);
        ResponseEntity<?> responseFind = commentService.findCommentById(commentId);
        ResponseEntity<HashMap<String, ?>> responseDelete = (ResponseEntity<HashMap<String, ?>>) commentService.deleteComment(commentId);
        assertEquals(commentId, responseUpdate.getBody().get("No ha encontrado el curso con id: "));
        assertEquals(HttpStatus.NOT_FOUND, responseUpdate.getStatusCode());
        assertEquals(commentId, ((HashMap<String, ?>) responseFind.getBody()).get("No ha encontrado el curso con id: "));
        assertEquals(HttpStatus.NOT_FOUND, responseFind.getStatusCode());
        assertEquals(commentId, responseDelete.getBody().get("No ha encontrado el curso con id: "));
        assertEquals(HttpStatus.NOT_FOUND, responseDelete.getStatusCode());
    }

    @Test
    void catchError() {
        int commentId = 1;
        when(commentRepository.save(any())).thenThrow(new RuntimeException("Error simulado"));
        when(commentRepository.existsById(any())).thenThrow(new RuntimeException("Error simulado"));
        ResponseEntity<HashMap<String, ?>> responseUpdate = (ResponseEntity<HashMap<String, ?>>) commentService.updateComment(commentId,commentDTO);
        ResponseEntity<?> responseFind = commentService.findCommentById(commentId);
        ResponseEntity<HashMap<String, ?>> responseDelete = (ResponseEntity<HashMap<String, ?>>) commentService.deleteComment(commentId);

        assertTrue(responseUpdate.getBody().containsKey("Error"));
        assertEquals("Error simulado", responseUpdate.getBody().get("Error"));
        assertEquals("Error simulado", ((HashMap<String, ?>) responseFind.getBody()).get("Error"));
        assertEquals("Error simulado", responseDelete.getBody().get("Error"));
    }

}
