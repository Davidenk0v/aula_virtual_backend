package com.grupo2.aulavirtual.tests.RepositoryTest;

import com.grupo2.aulavirtual.entities.CommentEntity;
import com.grupo2.aulavirtual.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CommentRepositoryTest {

    @MockBean
    private CommentRepository commentRepository;

    private CommentEntity commentEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        commentEntity = CommentEntity.builder()
                .idComment(1)
                .course(null)
                .user(null)
                .text("Comentario de prueba")
                .date(Date.valueOf("2024-09-01"))
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

}
