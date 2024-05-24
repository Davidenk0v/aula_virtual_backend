package com.grupo2.aulavirtual.tests.dtotest;

import com.grupo2.aulavirtual.payload.request.CommentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentDTOTest {

    CommentDTO commentDTO;

    @BeforeEach
    void setUp() {
        commentDTO = CommentDTO.builder()
                .idComment(1)
                .course(null)
                .text("DTO de prueba")
                .date(Date.valueOf("2024-09-01"))
                .build();
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, commentDTO.getIdComment(), "idComment getter must be equal to 1");
        commentDTO.setIdComment(2);
        assertEquals(2L, commentDTO.getIdComment(), "idComment setter must be equal to 2");

        assertNull(commentDTO.getCourse(), "Course getter must be null");


        assertEquals("DTO de prueba", commentDTO.getText(), "text getter must be equal to 'DTO de prueba'");
        commentDTO.setText("DTO actualizado");
        assertEquals("DTO actualizado", commentDTO.getText(), "text setter must be equal to 'DTO actualizado'");

        assertEquals(Date.valueOf("2024-09-01"), commentDTO.getDate(), "date getter must be equal to '2024-09-01'");
        commentDTO.setDate(Date.valueOf("2024-12-01"));
        assertEquals(Date.valueOf("2024-12-01"), commentDTO.getDate(), "date setter must be equal to '2024-12-01'");
    }

    @Test
    void testNotNull() {
        CommentDTO commentDTO = new CommentDTO();
        assertNotNull(commentDTO);
    }

}
