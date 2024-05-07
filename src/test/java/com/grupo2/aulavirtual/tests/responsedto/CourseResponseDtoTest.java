package com.grupo2.aulavirtual.tests.responsedto;

import com.grupo2.aulavirtual.payload.response.CourseResponseDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class CourseResponseDtoTest {
    @Test
    void testGettersAndSetters() {
        CourseResponseDto courseResponseDto = CourseResponseDto.builder()
                .idCourse(1L)
                .name("Java Programming")
                .description("Learn Java programming language")
                .startDate(Date.valueOf("2024-09-01"))
                .finishDate(Date.valueOf("2024-12-31"))
                .price(BigDecimal.valueOf(100))
                .build();

        assertEquals(1L, courseResponseDto.getIdCourse(), "idCourse getter must be equal to 1L");
        courseResponseDto.setIdCourse(2L);
        assertEquals(2L, courseResponseDto.getIdCourse(), "idCourse setter must be equal to 2L");

        assertEquals("Java Programming", courseResponseDto.getName(),
                "Name getter must be equal to 'Java Programming'");
        courseResponseDto.setName("Python Programming");
        assertEquals("Python Programming", courseResponseDto.getName(),
                "Name setter must be equal to 'Python Programming'");

        assertEquals("Learn Java programming language", courseResponseDto.getDescription(),
                "Description getter must be equal to 'Learn Java programming language'");
        courseResponseDto.setDescription("Learn Python programming language");
        assertEquals("Learn Python programming language", courseResponseDto.getDescription(),
                "Description setter must be equal to 'Learn Python programming language'");

        assertEquals(Date.valueOf("2024-09-01"), courseResponseDto.getStartDate(),
                "StartDate getter must be equal to '2024-09-01'");
        courseResponseDto.setStartDate(Date.valueOf("2025-01-01"));
        assertEquals(Date.valueOf("2025-01-01"), courseResponseDto.getStartDate(),
                "StartDate setter must be equal to '2025-01-01'");

        assertEquals(Date.valueOf("2024-12-31"), courseResponseDto.getFinishDate(),
                "FinishDate getter must be equal to '2024-12-31'");
        courseResponseDto.setFinishDate(Date.valueOf("2025-12-31"));
        assertEquals(Date.valueOf("2025-12-31"), courseResponseDto.getFinishDate(),
                "FinishDate setter must be equal to '2025-12-31'");

        assertEquals(BigDecimal.valueOf(100), courseResponseDto.getPrice(), "Pago getter must be equal to 100");
        courseResponseDto.setPrice(BigDecimal.valueOf(200));
        assertEquals(BigDecimal.valueOf(200), courseResponseDto.getPrice(), "Pago setter must be equal to 200");

    }

    @Test
    void testNotNull() {
        CourseResponseDto courseResponseDto = new CourseResponseDto();
        assertNotNull(courseResponseDto);
    }
}