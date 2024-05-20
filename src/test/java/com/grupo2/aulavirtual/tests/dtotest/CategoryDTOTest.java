package com.grupo2.aulavirtual.tests.dtotest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.grupo2.aulavirtual.payload.request.CategoryDTO;
import org.junit.jupiter.api.Test;



public class CategoryDTOTest {

    @Test
    void testGettersAndSetters() {
        CategoryDTO categoryDTO = CategoryDTO.builder()
            .idCategory(1L)
            .course(null)
            .build();

        assertEquals(1L, categoryDTO.getIdCategory(), "idCategory getter must be equal to 1L");
        categoryDTO.setIdCategory(2L);
        assertEquals(2L, categoryDTO.getIdCategory(), "idCategory setter must be equal to 2L");

        categoryDTO.setCategory("Cooking");
        assertEquals("Cooking", categoryDTO.getCategory(), "Category setter must be equal to CategoryEnum.SCIENCE");

        assertEquals(null, categoryDTO.getCourse(), "Course getter must be null");
    }

    @Test
    void testNotNull() {
        CategoryDTO categoryDTO = new CategoryDTO();
        assertNotNull(categoryDTO);
    }

}