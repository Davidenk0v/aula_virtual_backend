package com.grupo2.aulavirtual.Tests.DTOTests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.sql.Date;

import com.grupo2.aulavirtual.Payload.Request.CourseDTO;
import org.junit.jupiter.api.Test;


public class CourseDTOTest {

    @Test
    void testGettersAndSetters() {
        CourseDTO courseDTO = CourseDTO.builder()
            .idCourse(1L)
            .name("Java Programming")
            .description("Learn Java programming language")
            .startDate(Date.valueOf("2024-09-01"))
            .finishDate(Date.valueOf("2024-12-31"))
            .pago(BigDecimal.valueOf(100))
            .user(null)
            .build();

        assertEquals(1L, courseDTO.getIdCourse(), "idCourse getter must be equal to 1L");
        courseDTO.setIdCourse(2L);
        assertEquals(2L, courseDTO.getIdCourse(), "idCourse setter must be equal to 2L");

        assertEquals("Java Programming", courseDTO.getName(), "Name getter must be equal to 'Java Programming'");
        courseDTO.setName("Python Programming");
        assertEquals("Python Programming", courseDTO.getName(), "Name setter must be equal to 'Python Programming'");

        assertEquals("Learn Java programming language", courseDTO.getDescription(), "Description getter must be equal to 'Learn Java programming language'");
        courseDTO.setDescription("Learn Python programming language");
        assertEquals("Learn Python programming language", courseDTO.getDescription(), "Description setter must be equal to 'Learn Python programming language'");

        assertEquals(Date.valueOf("2024-09-01"), courseDTO.getStartDate(), "StartDate getter must be equal to '2024-09-01'");
        courseDTO.setStartDate(Date.valueOf("2025-01-01"));
        assertEquals(Date.valueOf("2025-01-01"), courseDTO.getStartDate(), "StartDate setter must be equal to '2025-01-01'");

        assertEquals(Date.valueOf("2024-12-31"), courseDTO.getFinishDate(), "FinishDate getter must be equal to '2024-12-31'");
        courseDTO.setFinishDate(Date.valueOf("2025-12-31"));
        assertEquals(Date.valueOf("2025-12-31"), courseDTO.getFinishDate(), "FinishDate setter must be equal to '2025-12-31'");

        assertEquals(BigDecimal.valueOf(100), courseDTO.getPago(), "Pago getter must be equal to 100");
        courseDTO.setPago(BigDecimal.valueOf(200));
        assertEquals(BigDecimal.valueOf(200), courseDTO.getPago(), "Pago setter must be equal to 200");

        assertEquals(null, courseDTO.getUser(), "User getter must be null");
    }

    @Test
    void testNotNull() {
        CourseDTO courseDTO = new CourseDTO();
        assertNotNull(courseDTO);
    }

}