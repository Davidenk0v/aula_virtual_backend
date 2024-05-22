package com.grupo2.aulavirtual.tests.responsedto;

import com.grupo2.aulavirtual.payload.response.CategoryResponseDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryResponseDtoTest {
    @Test
    void testGettersAndSetters() {
        CategoryResponseDto categoryResponseDto = CategoryResponseDto.builder()
                .idCategory(1L)
                .course(null)
                .build();

        assertEquals(1L, categoryResponseDto.getIdCategory(), "idCategory getter must be equal to 1L");
        categoryResponseDto.setIdCategory(2L);
        assertEquals(2L, categoryResponseDto.getIdCategory(), "idCategory setter must be equal to 2L");


        assertEquals(null, categoryResponseDto.getCourse(), "Course getter must be null");
    }

    @Test
    void testNotNull() {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        assertNotNull(categoryResponseDto);
    }
}