package com.grupo2.aulavirtual.tests.entitiestest;

import com.grupo2.aulavirtual.entities.CommentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CommentEntityTest {

    CommentEntity commentEntity;

    @BeforeEach
    void setUp() {
        commentEntity = CommentEntity.builder()
                .idComment(1)
                .course(null)
                .text("Comentario de prueba")
                .date(Date.valueOf("2024-09-01"))
                .build();
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, commentEntity.getIdComment(), "idComment getter must be equal to 1");
        commentEntity.setIdComment(2);
        assertEquals(2L, commentEntity.getIdComment(), "idComment setter must be equal to 2");

        assertNull(commentEntity.getCourse(), "Course getter must be null");


        assertEquals("Comentario de prueba", commentEntity.getText(), "text getter must be equal to 'Comentario de prueba'");
        commentEntity.setText("Comentario actualizado");
        assertEquals("Comentario actualizado", commentEntity.getText(), "text setter must be equal to 'Comentario actualizado'");

        assertEquals(Date.valueOf("2024-09-01"), commentEntity.getDate(), "date getter must be equal to '2024-09-01'");
        commentEntity.setDate(Date.valueOf("2024-12-01"));
        assertEquals(Date.valueOf("2024-12-01"), commentEntity.getDate(), "date setter must be equal to '2024-12-01'");
    }

    @Test
    void testNotNull() {
        CommentEntity commentEntity = new CommentEntity();
        assertNotNull(commentEntity);
    }

}
