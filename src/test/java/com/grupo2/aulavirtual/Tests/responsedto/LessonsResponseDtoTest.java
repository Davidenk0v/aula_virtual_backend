package com.grupo2.aulavirtual.tests.responsedto;

import com.grupo2.aulavirtual.Entities.LessonsEntity;
import com.grupo2.aulavirtual.Payload.Response.LessonsResponseDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LessonsResponseDtoTest {
    @Test
    void testGettersAndSetters() {
        LessonsResponseDto lessonsResponseDto = LessonsResponseDto.builder()
                .idLesson(1L)
                .name("Introduction to Programming")
                .contenido("Lesson content")
                .description("Description of the lesson")
                .subject(null)
                .build();

        assertEquals(1L, lessonsResponseDto.getIdLesson(), "idLesson getter must be equal to 1L");
        lessonsResponseDto.setIdLesson(2L);
        assertEquals(2L, lessonsResponseDto.getIdLesson(), "idLesson setter must be equal to 2L");

        assertEquals("Introduction to Programming", lessonsResponseDto.getName(), "Name getter must be equal to 'Introduction to Programming'");
        lessonsResponseDto.setName("Advanced Programming");
        assertEquals("Advanced Programming", lessonsResponseDto.getName(), "Name setter must be equal to 'Advanced Programming'");

        assertEquals("Lesson content", lessonsResponseDto.getContenido(), "Contenido getter must be equal to 'Lesson content'");
        lessonsResponseDto.setContenido("Advanced lesson content");
        assertEquals("Advanced lesson content", lessonsResponseDto.getContenido(), "Contenido setter must be equal to 'Advanced lesson content'");

        assertEquals("Description of the lesson", lessonsResponseDto.getDescription(), "Description getter must be equal to 'Description of the lesson'");
        lessonsResponseDto.setDescription("Advanced description");
        assertEquals("Advanced description", lessonsResponseDto.getDescription(), "Description setter must be equal to 'Advanced description'");

        assertEquals(null, lessonsResponseDto.getSubject(), "Subject getter must be null");
    }

    @Test
    void testNotNull() {
        LessonsResponseDto lessonsResponseDto = new LessonsResponseDto();
        assertNotNull(lessonsResponseDto);
    }
}