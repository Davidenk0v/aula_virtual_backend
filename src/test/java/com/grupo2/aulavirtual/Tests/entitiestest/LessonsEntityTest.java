package com.grupo2.aulavirtual.tests.entitiestest;

import com.grupo2.aulavirtual.entities.LessonsEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LessonsEntityTest {

    @Test
    void testGettersAndSetters() {
        LessonsEntity lessonsEntity = LessonsEntity.builder()
                .idLesson(1L)
                .name("Introduction to Programming")
                .contenido("Lesson content")
                .description("Description of the lesson")
                .subject(null)
                .build();

        assertEquals(1L, lessonsEntity.getIdLesson(), "idLesson getter must be equal to 1L");
        lessonsEntity.setIdLesson(2L);
        assertEquals(2L, lessonsEntity.getIdLesson(), "idLesson setter must be equal to 2L");

        assertEquals("Introduction to Programming", lessonsEntity.getName(), "Name getter must be equal to 'Introduction to Programming'");
        lessonsEntity.setName("Advanced Programming");
        assertEquals("Advanced Programming", lessonsEntity.getName(), "Name setter must be equal to 'Advanced Programming'");

        assertEquals("Lesson content", lessonsEntity.getContenido(), "Contenido getter must be equal to 'Lesson content'");
        lessonsEntity.setContenido("Advanced lesson content");
        assertEquals("Advanced lesson content", lessonsEntity.getContenido(), "Contenido setter must be equal to 'Advanced lesson content'");

        assertEquals("Description of the lesson", lessonsEntity.getDescription(), "Description getter must be equal to 'Description of the lesson'");
        lessonsEntity.setDescription("Advanced description");
        assertEquals("Advanced description", lessonsEntity.getDescription(), "Description setter must be equal to 'Advanced description'");

        assertEquals(null, lessonsEntity.getSubject(), "Subject getter must be null");
    }

    @Test
    void testNotNull() {
        LessonsEntity lessonsEntity = new LessonsEntity();
        assertNotNull(lessonsEntity);
    }
}