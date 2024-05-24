package com.grupo2.aulavirtual.tests.services;
import com.grupo2.aulavirtual.entities.CategoryEntity;
import com.grupo2.aulavirtual.payload.response.CategoryResponseDto;
import com.grupo2.aulavirtual.repositories.CategoryRepository;
import com.grupo2.aulavirtual.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void categoriesList_NoCategoriesFound_NotFoundResponse() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<?> responseEntity = categoryService.categoriesList();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("No se encontraron categorias", responseEntity.getBody());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void categoriesList_CategoriesFound_SuccessResponse() {
        // Arrange
        List<CategoryEntity> categories = new ArrayList<>();
        CategoryEntity category1 = new CategoryEntity();
        category1.setIdCategory(1L);
        category1.setCategory("Category 1");

        CategoryEntity category2 = new CategoryEntity();
        category2.setIdCategory(2L);
        category2.setCategory("Category 2");

        categories.add(category1);
        categories.add(category2);

        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        ResponseEntity<?> responseEntity = categoryService.categoriesList();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<CategoryResponseDto> responseBody = (List<CategoryResponseDto>) responseEntity.getBody();
        assertEquals(2, responseBody.size());
        assertEquals("Category 1", responseBody.get(0).getCategory());
        assertEquals("Category 2", responseBody.get(1).getCategory());
        verify(categoryRepository, times(1)).findAll();
    }
}
