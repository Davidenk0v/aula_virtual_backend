package com.grupo2.aulavirtual.tests.exception;

import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.MeetingEntity;
import com.grupo2.aulavirtual.payload.request.CourseDTO;
import com.grupo2.aulavirtual.repositories.CategoryRepository;
import com.grupo2.aulavirtual.repositories.CourseRepository;
import com.grupo2.aulavirtual.services.KeycloakService;
import com.grupo2.aulavirtual.services.impl.CoursesServiceImpl;
import com.grupo2.aulavirtual.util.files.FileUtil;
import com.grupo2.aulavirtual.util.mappers.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class exceptiontest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private KeycloakService keycloakService;

    @Mock
    private FileUtil fileUtil;

    @InjectMocks
    private CoursesServiceImpl coursesService;

    private CourseDTO courseDTO;
    private CourseEntity courseEntity;
    private DtoMapper dtoMapper = new DtoMapper();

    private static final String NOT_FOUND = "No encontrado";
    private static final String SAVE = "data";
    private static final String ERROR = "error";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        courseDTO = CourseDTO.builder()
                .idCourse(1L) // Este campo puede variar dependiendo de cómo se use el DTO
                .name("Curso de ejemplo")
                .description("Descripción del curso")
                .startDate(Date.valueOf("2024-05-01")) // Ejemplo de fecha de inicio
                .finishDate(Date.valueOf("2024-06-30")) // Ejemplo de fecha de finalización
                .price(BigDecimal.valueOf(100)) // Ejemplo de monto de pago
                // Añadir otras configuraciones según sea necesario
                .build();
        courseEntity = dtoMapper.dtoToEntity(courseDTO);
    }
    @Test
    void catchError() {
        Long courseId = 1L;

        when(courseRepository.save(any())).thenThrow(new RuntimeException("Error simulado"));
        when(courseRepository.existsById(any())).thenThrow(new RuntimeException("Error simulado"));
        when(courseRepository.findById(any())).thenThrow(new RuntimeException("Error simulado"));
        when(fileUtil.updateFile(any(),any(),any())).thenThrow(new RuntimeException("Error simulado"));
        when(courseRepository.findByKeyword(anyString())).thenThrow(new RuntimeException("Error simulado"));

        ResponseEntity<HashMap<String, ?>> responseUpdate = coursesService.updateCourse(courseId, courseDTO);
        ResponseEntity<?> responseFind = coursesService.findCourseById(courseId);
        ResponseEntity<?> responsePost = coursesService.postCourse("",courseDTO);
        ResponseEntity<?> responseFindCategory = coursesService.findCoursesByCategory("courseId");
        ResponseEntity<?> responseFindByUser = coursesService.findCoursesByUser("courseId");
        ResponseEntity<?> responseFindCoursesByCategory = coursesService.findCoursesByCategory("courseId");
        ResponseEntity<?> responseFindAllByContains = coursesService.findAllByContains("courseId");
        ResponseEntity<?> responseDelete = coursesService.deleteCourse(courseId);
        ResponseEntity<?> responseSetDefaultImage = coursesService.updateFile(any(),any());
        ResponseEntity<?> responseSaveDefaultImage = coursesService.saveFile(any(),any());


        HashMap<String,? >responsePostDef  = (HashMap<String, ?>) responsePost.getBody();
        HashMap<String,? >responseFindDef  = (HashMap<String, ?>) responseFind.getBody();
        HashMap<String,? >responseSetDefaultImageDef  = (HashMap<String, ?>) responseSetDefaultImage.getBody();
        HashMap<String,? >responseSaveDefaultImageDef  = (HashMap<String, ?>) responseSaveDefaultImage.getBody();
        HashMap<String,? >responseDeleteDef  = (HashMap<String, ?>) responseDelete.getBody();
        HashMap<String,? >responseFindCategoryDef  = (HashMap<String, ?>) responseFindCategory.getBody();
        HashMap<String,? >responseFindCoursesByCategoryDef  = (HashMap<String, ?>) responseFindCoursesByCategory.getBody();
        HashMap<String,? >responseFindAllByContainsDef = (HashMap<String, ?>) responseFindAllByContains.getBody();
        HashMap<String,? >responseFindByUserDef  = (HashMap<String, ?>) responseFindByUser.getBody();

        assertTrue(responseUpdate.getBody().containsKey(ERROR));
        assertEquals("Error simulado", responseUpdate.getBody().get(ERROR));

        assertTrue(responseSetDefaultImageDef.containsKey("Error"));
        assertTrue(responseSaveDefaultImageDef.containsKey(ERROR));

        assertTrue(responseFindDef.containsKey(ERROR));
        assertEquals("Error simulado", responseFindDef.get(ERROR));

        assertTrue(responseFindCoursesByCategoryDef.containsKey(ERROR));
        assertEquals("courseId", responseFindCoursesByCategoryDef.get(ERROR));

        assertTrue(responsePostDef.containsKey(ERROR));

        assertTrue(responseFindAllByContainsDef.containsKey(ERROR));
        assertEquals("Error simulado", responseFindAllByContainsDef.get(ERROR));

        assertTrue(responseFindByUserDef.containsKey(ERROR));
        assertEquals("courseId", responseFindByUserDef.get(ERROR));

        assertTrue(responseDeleteDef.containsKey(ERROR));
        assertEquals("Error simulado", responseDeleteDef.get(ERROR));

        assertTrue(responseFindCategoryDef.containsKey(ERROR));
        assertEquals("courseId", responseFindCategoryDef.get(ERROR));
    }
}
