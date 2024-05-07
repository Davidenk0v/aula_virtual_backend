package com.grupo2.aulavirtual.tests.responsedto;

import com.grupo2.aulavirtual.payload.response.SubjectsResponseDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubjectsResponseDtoTest {

    @Test
    void testGettersAndSetters() {
        SubjectsResponseDto subjectsResponseDto = SubjectsResponseDto.builder()
                .idSubject(1L)
                .name("Math")
                .description("Mathematics subject")
                .course(null)
                .lessons(null)
                .build();

        assertEquals(1L, subjectsResponseDto.getIdSubject(), "idSubject getter must be equal to 1L");
        subjectsResponseDto.setIdSubject(2L);
        assertEquals(2L, subjectsResponseDto.getIdSubject(), "idSubject setter must be equal to 2L");

        assertEquals("Math", subjectsResponseDto.getName(), "Name getter must be equal to 'Math'");
        subjectsResponseDto.setName("Science");
        assertEquals("Science", subjectsResponseDto.getName(), "Name setter must be equal to 'Science'");

        assertEquals("Mathematics subject", subjectsResponseDto.getDescription(),
                "Description getter must be equal to 'Mathematics subject'");
        subjectsResponseDto.setDescription("Science subject");
        assertEquals("Science subject", subjectsResponseDto.getDescription(),
                "Description setter must be equal to 'Science subject'");

        assertNull(subjectsResponseDto.getCourse(), "Course getter must be null");
        assertNull(subjectsResponseDto.getLessons(), "Lessons getter must be null");
    }

    @Test
    void testNotNull() {
        SubjectsResponseDto subjectsResponseDto = new SubjectsResponseDto();
        assertNotNull(subjectsResponseDto);
    }
}