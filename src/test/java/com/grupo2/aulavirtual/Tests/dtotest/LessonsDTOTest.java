package com.grupo2.aulavirtual.tests.dtotest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.grupo2.aulavirtual.payload.request.LessonsDTO;
import org.junit.jupiter.api.Test;

public class LessonsDTOTest {

    @Test
    void testGettersAndSetters() {
        LessonsDTO lessonsDTO = LessonsDTO.builder()
            .idLesson(1L)
            .name("Introduction to Programming")
            .contenido("Lesson content")
            .description("Description of the lesson")
            .subject(null)
            .build();

        assertEquals(1L, lessonsDTO.getIdLesson(), "idLesson getter must be equal to 1L");
        lessonsDTO.setIdLesson(2L);
        assertEquals(2L, lessonsDTO.getIdLesson(), "idLesson setter must be equal to 2L");

        assertEquals("Introduction to Programming", lessonsDTO.getName(), "Name getter must be equal to 'Introduction to Programming'");
        lessonsDTO.setName("Advanced Programming");
        assertEquals("Advanced Programming", lessonsDTO.getName(), "Name setter must be equal to 'Advanced Programming'");

        assertEquals("Lesson content", lessonsDTO.getContenido(), "Contenido getter must be equal to 'Lesson content'");
        lessonsDTO.setContenido("Advanced lesson content");
        assertEquals("Advanced lesson content", lessonsDTO.getContenido(), "Contenido setter must be equal to 'Advanced lesson content'");

        assertEquals("Description of the lesson", lessonsDTO.getDescription(), "Description getter must be equal to 'Description of the lesson'");
        lessonsDTO.setDescription("Advanced description");
        assertEquals("Advanced description", lessonsDTO.getDescription(), "Description setter must be equal to 'Advanced description'");

        assertEquals(null, lessonsDTO.getSubject(), "Subject getter must be null");
    }

    @Test
    void testNotNull() {
        LessonsDTO lessonsDTO = new LessonsDTO();
        assertNotNull(lessonsDTO);
    }

}