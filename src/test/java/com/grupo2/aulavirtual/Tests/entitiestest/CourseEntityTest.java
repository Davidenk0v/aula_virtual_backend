package com.grupo2.aulavirtual.tests.entitiestest;

import com.grupo2.aulavirtual.Entities.CourseEntity;
import com.grupo2.aulavirtual.Payload.Request.CourseDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class CourseEntityTest {

    @Test
    void testGettersAndSetters() {
        CourseEntity courseEntity = CourseEntity.builder()
                .idCourse(1L)
                .name("Java Programming")
                .description("Learn Java programming language")
                .startDate(Date.valueOf("2024-09-01"))
                .finishDate(Date.valueOf("2024-12-31"))
                .pago(BigDecimal.valueOf(100))
                .user(null)
                .build();

        assertEquals(1L, courseEntity.getIdCourse(), "idCourse getter must be equal to 1L");
        courseEntity.setIdCourse(2L);
        assertEquals(2L, courseEntity.getIdCourse(), "idCourse setter must be equal to 2L");

        assertEquals("Java Programming", courseEntity.getName(), "Name getter must be equal to 'Java Programming'");
        courseEntity.setName("Python Programming");
        assertEquals("Python Programming", courseEntity.getName(), "Name setter must be equal to 'Python Programming'");

        assertEquals("Learn Java programming language", courseEntity.getDescription(), "Description getter must be equal to 'Learn Java programming language'");
        courseEntity.setDescription("Learn Python programming language");
        assertEquals("Learn Python programming language", courseEntity.getDescription(), "Description setter must be equal to 'Learn Python programming language'");

        assertEquals(Date.valueOf("2024-09-01"), courseEntity.getStartDate(), "StartDate getter must be equal to '2024-09-01'");
        courseEntity.setStartDate(Date.valueOf("2025-01-01"));
        assertEquals(Date.valueOf("2025-01-01"), courseEntity.getStartDate(), "StartDate setter must be equal to '2025-01-01'");

        assertEquals(Date.valueOf("2024-12-31"), courseEntity.getFinishDate(), "FinishDate getter must be equal to '2024-12-31'");
        courseEntity.setFinishDate(Date.valueOf("2025-12-31"));
        assertEquals(Date.valueOf("2025-12-31"), courseEntity.getFinishDate(), "FinishDate setter must be equal to '2025-12-31'");

        assertEquals(BigDecimal.valueOf(100), courseEntity.getPago(), "Pago getter must be equal to 100");
        courseEntity.setPago(BigDecimal.valueOf(200));
        assertEquals(BigDecimal.valueOf(200), courseEntity.getPago(), "Pago setter must be equal to 200");

        assertEquals(null, courseEntity.getUser(), "User getter must be null");
    }

    @Test
    void testNotNull() {
        CourseEntity courseEntity = new CourseEntity();
        assertNotNull(courseEntity);
    }
}