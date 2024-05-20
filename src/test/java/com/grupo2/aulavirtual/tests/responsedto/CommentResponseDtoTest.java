package com.grupo2.aulavirtual.tests.responsedto;

import com.grupo2.aulavirtual.payload.request.CommentDTO;
import com.grupo2.aulavirtual.payload.response.CommentResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CommentResponseDtoTest {

    CommentResponseDto commentResponseDto;

    @BeforeEach
    void setUp() {
        commentResponseDto = CommentResponseDto.builder()
                .idComment(1)
                .course(null)
                .user(null)
                .text("ResponseDTO de prueba")
                .date(Date.valueOf("2024-09-01"))
                .build();
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, commentResponseDto.getIdComment(), "idComment getter must be equal to 1");
        commentResponseDto.setIdComment(2);
        assertEquals(2L, commentResponseDto.getIdComment(), "idComment setter must be equal to 2");

        assertNull(commentResponseDto.getCourse(), "Course getter must be null");

        assertNull(commentResponseDto.getUser(), "User getter must be null");

        assertEquals("ResponseDTO de prueba", commentResponseDto.getText(), "text getter must be equal to 'ResponseDTO de prueba'");
        commentResponseDto.setText("ResponseDTO actualizado");
        assertEquals("ResponseDTO actualizado", commentResponseDto.getText(), "text setter must be equal to 'ResponseDTO actualizado'");

        assertEquals(Date.valueOf("2024-09-01"), commentResponseDto.getDate(), "date getter must be equal to '2024-09-01'");
        commentResponseDto.setDate(Date.valueOf("2024-12-01"));
        assertEquals(Date.valueOf("2024-12-01"), commentResponseDto.getDate(), "date setter must be equal to '2024-12-01'");
    }

    @Test
    void testNotNull() {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        assertNotNull(commentResponseDto);
    }

}
