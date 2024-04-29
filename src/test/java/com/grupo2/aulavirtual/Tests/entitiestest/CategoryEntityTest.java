package com.grupo2.aulavirtual.tests.entitiestest;

import com.grupo2.aulavirtual.Entities.CategoryEntity;
import com.grupo2.aulavirtual.Enum.CategoryEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryEntityTest {

    @Test
    void testGettersAndSetters() {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .idCategory(1L)
                .category(CategoryEnum.DESIGN)
                .course(null)
                .build();

        assertEquals(1L, categoryEntity.getIdCategory(), "idCategory getter must be equal to 1L");
        categoryEntity.setIdCategory(2L);
        assertEquals(2L, categoryEntity.getIdCategory(), "idCategory setter must be equal to 2L");

        assertEquals(CategoryEnum.DESIGN, categoryEntity.getCategory(), "Category getter must be equal to CategoryEnum.MATH");
        categoryEntity.setCategory(CategoryEnum.COOKING);
        assertEquals(CategoryEnum.COOKING, categoryEntity.getCategory(), "Category setter must be equal to CategoryEnum.SCIENCE");

        assertEquals(null, categoryEntity.getCourse(), "Course getter must be null");
    }

    @Test
    void testNotNull() {
        CategoryEntity categoryEntity = new CategoryEntity();
        assertNotNull(categoryEntity);
    }
}