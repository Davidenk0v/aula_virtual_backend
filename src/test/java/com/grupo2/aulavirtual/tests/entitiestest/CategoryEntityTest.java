package com.grupo2.aulavirtual.tests.entitiestest;

import com.grupo2.aulavirtual.entities.CategoryEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryEntityTest {

    @Test
    void testGettersAndSetters() {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .idCategory(1L)
                .course(null)
                .build();

        assertEquals(1L, categoryEntity.getIdCategory(), "idCategory getter must be equal to 1L");
        categoryEntity.setIdCategory(2L);
        assertEquals(2L, categoryEntity.getIdCategory(), "idCategory setter must be equal to 2L");


        assertEquals(null, categoryEntity.getCourse(), "Course getter must be null");
    }

    @Test
    void testNotNull() {
        CategoryEntity categoryEntity = new CategoryEntity();
        assertNotNull(categoryEntity);
    }
}