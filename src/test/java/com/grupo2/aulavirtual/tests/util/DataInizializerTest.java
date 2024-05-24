package com.grupo2.aulavirtual.tests.util;
import com.grupo2.aulavirtual.entities.CategoryEntity;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.repositories.CategoryRepository;
import com.grupo2.aulavirtual.repositories.CourseRepository;
import com.grupo2.aulavirtual.util.database.DataInizializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataInizializerTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private DataInizializer dataInizializer;

    @BeforeEach
    void setUp() {
        // Reset mocks before each test
        reset(categoryRepository, courseRepository);
    }

    @Test
    void run_CategoryAndCourseRepositoriesEmpty_InsertsData() throws Exception {
        // Arrange
        when(categoryRepository.count()).thenReturn(0L);
        when(courseRepository.count()).thenReturn(0L);

        // Act
        dataInizializer.run();

        // Assert
        verify(categoryRepository, times(1)).count();
        verify(courseRepository, times(1)).count();
        verify(categoryRepository, times(1)).saveAll(anyList());
        verify(courseRepository, times(1)).saveAll(anyList());
    }

    @Test
    void run_CategoryAndCourseRepositoriesNotEmpty_NoInsertion() throws Exception {
        // Arrange
        when(categoryRepository.count()).thenReturn(1L);
        when(courseRepository.count()).thenReturn(1L);

        // Act
        dataInizializer.run();

        // Assert
        verify(categoryRepository, times(1)).count();
        verify(courseRepository, times(1)).count();
        verify(categoryRepository, never()).saveAll(anyList());
        verify(courseRepository, never()).saveAll(anyList());
    }
}
