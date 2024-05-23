package com.grupo2.aulavirtual.tests.services;

import com.grupo2.aulavirtual.entities.CommentEntity;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.UserImg;
import com.grupo2.aulavirtual.util.files.FileUtil;
import com.grupo2.aulavirtual.util.mappers.DtoMapper;
import com.grupo2.aulavirtual.payload.request.CommentDTO;
import com.grupo2.aulavirtual.payload.request.CourseDTO;
import com.grupo2.aulavirtual.payload.request.UserDTO;
import com.grupo2.aulavirtual.payload.response.CommentResponseDto;
import com.grupo2.aulavirtual.payload.response.CourseResponseDto;
import com.grupo2.aulavirtual.payload.response.UserResponseDto;
import com.grupo2.aulavirtual.repositories.CommentRepository;
import com.grupo2.aulavirtual.repositories.CourseRepository;
import com.grupo2.aulavirtual.repositories.ImageRepository;
import com.grupo2.aulavirtual.services.KeycloakService;
import com.grupo2.aulavirtual.services.impl.CommentServiceImpl;
import com.grupo2.aulavirtual.services.impl.CoursesServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private KeycloakService keycloakService;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private FileUtil fileUtil;

    private DtoMapper dtoMapper;

    @InjectMocks
    private CommentServiceImpl commentService = new CommentServiceImpl();

    private CommentDTO commentDTO;
    private CommentResponseDto commentResponseDto;
    private CommentEntity commentEntity;
    private CourseDTO courseDTO;
    private CourseResponseDto courseResponseDto;
    private CourseEntity courseEntity;
    private UserDTO userDTO;
    private UserResponseDto userResponseDto;

    private static final String SAVE = "data";
    private static final String ERROR = "error";

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {

        Field defaultImgField = CommentServiceImpl.class.getDeclaredField("defaultImg");
        defaultImgField.setAccessible(true);
        defaultImgField.set(commentService, "defaultImage.png");

        Field coursesServiceImpl = CommentServiceImpl.class.getDeclaredField("userFolder");
        coursesServiceImpl.setAccessible(true);
        coursesServiceImpl.set(commentService, "/path/to/Users/");

        MockitoAnnotations.openMocks(this);
        dtoMapper = new DtoMapper();
        commentDTO = CommentDTO.builder()
                .idComment(1)
                .course(null)
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

        courseEntity = dtoMapper.dtoToEntity(courseDTO);

        commentEntity = new CommentEntity();
        commentEntity.setIdComment(1);
        commentEntity.setText("DTO de prueba");
        commentEntity.setDate(Date.valueOf("2024-09-01"));

        commentResponseDto = new CommentResponseDto();
        commentResponseDto.setIdComment(1);
        commentResponseDto.setText("DTO de prueba");
        commentResponseDto.setDate(Date.valueOf("2024-09-01"));
    }

    @Test
    void commentListEmpty() {
        when(commentRepository.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = commentService.commentList();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ERROR, response.getBody());
    }

    @Test
    void postComment_Success() {
        // Arrange
        String idUser = "user1";
        Long idCourse = 1L;
        CommentDTO commentDTO = new CommentDTO();
        CourseEntity courseEntity = new CourseEntity();
        CommentEntity commentEntity = new CommentEntity();
        CommentResponseDto responseDto = new CommentResponseDto();

        when(courseRepository.findById(idCourse)).thenReturn(Optional.of(courseEntity));

        // Act
        ResponseEntity<?> response = commentService.postComment(idUser, idCourse, commentDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }



    @Test
    void updateComment() {
        int commentId = 1;
        CommentDTO updatedCommentDTO = CommentDTO.builder()
                .text("Prueba")
                .date(Date.valueOf("2024-12-01"))
                .build();

        when(commentRepository.existsById(commentId)).thenReturn(true);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(new CommentEntity()));

        ResponseEntity<HashMap<String, ?>> response = (ResponseEntity<HashMap<String, ?>>) commentService.updateComment(commentId, updatedCommentDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().containsKey(SAVE));
    }

    @Test
    void findCommentById() {
        int commentId = 1;
        when(commentRepository.existsById(commentId)).thenReturn(true);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(commentEntity));

        ResponseEntity<?> response = commentService.findCommentById(commentId);
        CommentResponseDto expected = (CommentResponseDto) response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected.getText(), commentResponseDto.getText());
    }

    @Test
    void idNotFound() {
        int commentId = 1;
        when(commentRepository.existsById(commentId)).thenReturn(false);

        ResponseEntity<?> responseUpdate = commentService.updateComment(commentId, commentDTO);
        ResponseEntity<?> responseFind = commentService.findCommentById(commentId);
        ResponseEntity<?> responseDelete = commentService.deleteComment(commentId);


        HashMap<String,Integer> responseUpdateExpected = (HashMap<String, Integer>) responseUpdate.getBody();
        HashMap<String,Integer> responseFindExpected = (HashMap<String, Integer>) responseFind.getBody();
        HashMap<String,Integer> responseDeleteExpected = (HashMap<String, Integer>) responseFind.getBody();

        assertEquals(commentId, responseUpdateExpected.get(ERROR));
        assertEquals(HttpStatus.NOT_FOUND, responseUpdate.getStatusCode());
        assertEquals(commentId, responseFindExpected.get(ERROR));
        assertEquals(HttpStatus.NOT_FOUND, responseFind.getStatusCode());
        assertEquals(commentId, responseDeleteExpected.get(ERROR));
        assertEquals(HttpStatus.NOT_FOUND, responseDelete.getStatusCode());
    }

    @Test
    void catchError() {
        int commentId = 1;
        when(commentRepository.save(any())).thenThrow(new RuntimeException("Error simulado"));
        when(commentRepository.existsById(any())).thenThrow(new RuntimeException("Error simulado"));

        ResponseEntity<HashMap<String, ?>> responseUpdate = (ResponseEntity<HashMap<String, ?>>) commentService.updateComment(commentId, commentDTO);
        ResponseEntity<?> responseFind = commentService.findCommentById(commentId);
        ResponseEntity<HashMap<String, ?>> responseDelete = (ResponseEntity<HashMap<String, ?>>) commentService.deleteComment(commentId);

        assertTrue(responseUpdate.getBody().containsKey(ERROR));
        assertEquals("Error simulado", responseUpdate.getBody().get(ERROR));
        assertEquals("Error simulado", ((HashMap<String, ?>) responseFind.getBody()).get(ERROR));
        assertEquals("Error simulado", responseDelete.getBody().get(ERROR));
    }

    @Test
    void sendFile_KeycloakUser_NotFound() {
        // Arrange

        String userId = "user1";
        when(keycloakService.findUserById(userId)).thenReturn(null);

        // Act
        ResponseEntity<?> response = commentService.sendFile(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("error", response.getBody());
    }

    @Test
    void sendFile_User_NotFound() {
        // Arrange
        String userId = "user1";
        UserRepresentation userRepresentation = mock(UserRepresentation.class);

        when(keycloakService.findUserById(userId)).thenReturn(userRepresentation);
        when(imageRepository.findByIdUser(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = commentService.sendFile(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("error", response.getBody());
    }

    @Test
    void sendFile_File_NotFound() {
        // Arrange
        String userId = "user1";
        UserRepresentation userRepresentation = mock(UserRepresentation.class);
        String urlImg = "image.jpg";
        UserImg userImg = new UserImg(1L, userId, urlImg);

        when(keycloakService.findUserById(userId)).thenReturn(userRepresentation);
        when(imageRepository.findByIdUser(userId)).thenReturn(Optional.of(userImg));
        when(fileUtil.sendFile(anyString(), anyString())).thenReturn(new byte[0]);

        // Act
        ResponseEntity<?> response = commentService.sendFile(userId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("error", response.getBody());
    }

    @Test
    void sendFile_FileFound() {
        // Arrange
        String userId = "user1";
        UserRepresentation userRepresentation = mock(UserRepresentation.class);
        String urlImg = "image.jpeg";
        UserImg userImg = new UserImg(1L, userId, urlImg);
        byte[] fileContent = "fileContent".getBytes();

        when(keycloakService.findUserById(userId)).thenReturn(userRepresentation);
        when(imageRepository.findByIdUser(userId)).thenReturn(Optional.of(userImg));
        when(fileUtil.getExtensionByPath(anyString())).thenReturn("jpeg");
        when(fileUtil.getMediaType(anyString())).thenReturn("image/jpeg");
        when(fileUtil.sendFile(anyString(), anyString())).thenReturn(fileContent);
        
        // Act
        ResponseEntity<?> response = commentService.sendFile(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
        assertEquals(fileContent, response.getBody());
    }

}