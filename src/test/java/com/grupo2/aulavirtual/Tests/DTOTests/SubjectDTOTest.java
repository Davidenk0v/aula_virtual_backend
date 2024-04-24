package com.grupo2.aulavirtual.Tests.DTOTests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.grupo2.aulavirtual.Payload.Request.SubjectDTO;
import org.junit.jupiter.api.Test;


public class SubjectDTOTest {

    @Test
    void testGettersAndSetters() {
        SubjectDTO subjectDTO = SubjectDTO.builder()
            .idSubject(1L)
            .name("Math")
            .description("Mathematics subject")
            .course(null)
            .lessons(null)
            .build();

        assertEquals(1L, subjectDTO.getIdSubject(), "idSubject getter must be equal to 1L");
        subjectDTO.setIdSubject(2L);
        assertEquals(2L, subjectDTO.getIdSubject(), "idSubject setter must be equal to 2L");

        assertEquals("Math", subjectDTO.getName(), "Name getter must be equal to 'Math'");
        subjectDTO.setName("Science");
        assertEquals("Science", subjectDTO.getName(), "Name setter must be equal to 'Science'");

        assertEquals("Mathematics subject", subjectDTO.getDescription(), "Description getter must be equal to 'Mathematics subject'");
        subjectDTO.setDescription("Science subject");
        assertEquals("Science subject", subjectDTO.getDescription(), "Description setter must be equal to 'Science subject'");

        assertEquals(null, subjectDTO.getCourse(), "Course getter must be null");
        assertEquals(null, subjectDTO.getLessons(), "Lessons getter must be null");
    }

    @Test
    void testNotNull() {
        SubjectDTO subjectDTO = new SubjectDTO();
        assertNotNull(subjectDTO);
    }

}
