package com.grupo2.aulavirtual.tests.entitiestest;

import com.grupo2.aulavirtual.entities.SubjectsEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubjectsEntityTest {

    @Test
    void testGettersAndSetters() {
        SubjectsEntity subjectsEntity = SubjectsEntity.builder()
                .idSubject(1L)
                .name("Math")
                .description("Mathematics subject")
                .course(null)
                .lessons(null)
                .build();

        assertEquals(1L, subjectsEntity.getIdSubject(), "idSubject getter must be equal to 1L");
        subjectsEntity.setIdSubject(2L);
        assertEquals(2L, subjectsEntity.getIdSubject(), "idSubject setter must be equal to 2L");

        assertEquals("Math", subjectsEntity.getName(), "Name getter must be equal to 'Math'");
        subjectsEntity.setName("Science");
        assertEquals("Science", subjectsEntity.getName(), "Name setter must be equal to 'Science'");

        assertEquals("Mathematics subject", subjectsEntity.getDescription(),
                "Description getter must be equal to 'Mathematics subject'");
        subjectsEntity.setDescription("Science subject");
        assertEquals("Science subject", subjectsEntity.getDescription(),
                "Description setter must be equal to 'Science subject'");

        assertNull(subjectsEntity.getCourse(), "Course getter must be null");
        assertNull(subjectsEntity.getLessons(), "Lessons getter must be null");
    }

    @Test
    void testNotNull() {
        SubjectsEntity subjectsEntity = new SubjectsEntity();
        assertNotNull(subjectsEntity);
    }
}