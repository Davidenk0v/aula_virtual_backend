package com.grupo2.aulavirtual.tests.RepositoryTest;

import com.grupo2.aulavirtual.entities.CommentEntity;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.payload.request.CourseDTO;
import com.grupo2.aulavirtual.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CommentRepositoryTest {

    @MockBean
    private CommentRepository commentRepository;

    private CommentEntity commentEntity;

    private CommentEntity commentEntity2;

    private CourseEntity courseEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        courseEntity = courseEntity.builder()
                .idCourse(1L)
                .name("Curso de ejemplo")
                .description("Descripción del curso")
                .startDate(Date.valueOf("2024-05-01"))
                .finishDate(Date.valueOf("2024-06-30"))
                .price(BigDecimal.valueOf(100))
                .build();

        commentEntity = CommentEntity.builder()
                .idComment(1)
                .course(courseEntity)
                .user(null)
                .text("Comentario de prueba")
                .date(Date.valueOf("2024-09-01"))
                .build();

        commentEntity2 = CommentEntity.builder()
                .idComment(2)
                .course(courseEntity)
                .user(null)
                .text("Comentario de prueba 2")
                .date(Date.valueOf("2024-10-02"))
                .build();
    }

    @Test
    public void saveCommentTest() {
        // Arrange
        when(commentRepository.save(commentEntity)).thenReturn(commentEntity);
        // Act
        CommentEntity savedComment = commentRepository.save(commentEntity);
        // Assert
        assertEquals(commentEntity, savedComment, "Saved comment should match the original comment");
    }

    @Test
    public void deleteCommentTest() {
        // Arrange
        when(commentRepository.existsById(commentEntity.getIdComment())).thenReturn(true);
        // Act
        boolean userExists = commentRepository.existsById(commentEntity.getIdComment());
        commentRepository.deleteById(commentEntity.getIdComment());
        // Assert
        assertTrue(userExists, "Comment should exist before deletion");
        verify(commentRepository, times(1)).existsById(commentEntity.getIdComment());
        verify(commentRepository, times(1)).deleteById(commentEntity.getIdComment());
    }

    @Test
    public void findCommentsByIdCourse() {
        List<CommentEntity> comments = Arrays.asList(commentEntity, commentEntity2);
        when(commentRepository.findCommentsByIdCourse(anyLong())).thenReturn(comments);
        List<CommentEntity> result = commentRepository.findCommentsByIdCourse(1L);
        assertEquals(2, result.size());
        assertEquals("Comment 1", result.get(0).getText());
        assertEquals("Comment 2", result.get(1).getText());
    }

}
