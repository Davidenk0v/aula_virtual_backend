package com.grupo2.aulavirtual.tests.services;

import com.grupo2.aulavirtual.entities.CategoryEntity;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.payload.request.CourseDTO;
import com.grupo2.aulavirtual.payload.response.CourseResponseDto;
import com.grupo2.aulavirtual.repositories.CategoryRepository;
import com.grupo2.aulavirtual.repositories.CourseRepository;
import com.grupo2.aulavirtual.services.KeycloakService;
import com.grupo2.aulavirtual.services.impl.CoursesServiceImpl;
import com.grupo2.aulavirtual.util.files.FileUtil;
import com.grupo2.aulavirtual.util.mappers.DtoMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CoursesServiceTest {

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
    @Order(1)
    void courseListEmpty() {
        // Configurar el comportamiento del repositorio de cursos
        when(courseRepository.findAll()).thenReturn(Collections.emptyList());

        // Ejecutar el método bajo prueba
        ResponseEntity<?> response = coursesService.courseList();

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ERROR, response.getBody());
    }

    @Test
    @Order(2)
    void courseListNotEmpty() {
        List<CourseEntity> courseEntities = List.of(courseEntity);

        when(courseRepository.findAll()).thenReturn(courseEntities);

        ResponseEntity<?> response = coursesService.courseList();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        List<?> body = (List<?>) response.getBody();
        assertEquals(1, body.size());
    }

    @Test
    @Order(3)
    void pageableCourseList() {
        List<CourseEntity> courseEntities = List.of(courseEntity);
        Page<CourseEntity> coursePage = new PageImpl<>(courseEntities);
        Pageable pageable = PageRequest.of(0, 3);

        when(courseRepository.findAll(pageable)).thenReturn(coursePage);

        ResponseEntity<?> response = coursesService.pageableCourseList(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        List<?> body = (List<?>) response.getBody();
        assertEquals(1, body.size());
    }

    @Test
    @Order(4)
    void postCourse() {
        String idKeycloak = "123";
        UserRepresentation user = new UserRepresentation();
        user.setId(idKeycloak);

        when(keycloakService.findUserById(idKeycloak)).thenReturn(user);
        when(courseRepository.save(any(CourseEntity.class))).thenReturn(courseEntity);
        when(fileUtil.setDefaultImage(anyString())).thenReturn("defaultImgPath");

        ResponseEntity<?> response = coursesService.postCourse(idKeycloak, courseDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof CourseEntity);
    }

    @Test
    @Order(5)
    void postCourseUserNotFound() {
        String idKeycloak = "123";

        when(keycloakService.findUserById(idKeycloak)).thenReturn(null);

        ResponseEntity<?> response = coursesService.postCourse(idKeycloak, courseDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof HashMap);
    }

    @Test
    @Order(6)
    void updateCourse() {
        Long courseId = 1L;
        CourseEntity course = new CourseEntity();

        when(courseRepository.existsById(courseId)).thenReturn(true);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        ResponseEntity<HashMap<String, ?>> response = coursesService.updateCourse(courseId, courseDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().containsKey(SAVE));
    }

    @Test
    @Order(7)
    void findCourseById() {
        Long courseId = 1L;

        when(courseRepository.existsById(courseId)).thenReturn(true);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));

        ResponseEntity<?> response = coursesService.findCourseById(courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(8)
    void idNotFound() {
        Long courseId = 2L;
        when(courseRepository.existsById(courseId)).thenReturn(false);

        ResponseEntity<HashMap<String, ?>> responseUpdate = coursesService.updateCourse(courseId, courseDTO);
        ResponseEntity<?> responseFind = coursesService.findCourseById(courseId);
        ResponseEntity<?> responseDelete = coursesService.deleteCourse(courseId);

        assertEquals(courseId, responseUpdate.getBody().get(ERROR));
        assertEquals(HttpStatus.NOT_FOUND, responseUpdate.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, responseFind.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, responseDelete.getStatusCode());
    }

    @Test
    @Order(9)
    void deleteCourse() {
        Long courseId = 1L;

        when(courseRepository.existsById(courseId)).thenReturn(true);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));

        ResponseEntity<?> response = coursesService.deleteCourse(courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }




    @Test
    @Order(10)
    void findCoursesByCategory() {
        String category = "Programming";
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategory(category);

        when(categoryRepository.findByCategoryContaining(anyString())).thenReturn(Optional.of(categoryEntity));
        when(courseRepository.findCoursesByCategory(categoryEntity)).thenReturn(Set.of(courseEntity));

        ResponseEntity<?> response = coursesService.findCoursesByCategory(category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(11)
    void findCoursesByUser() {
        String userId = "123";
        Set<CourseEntity> courseEntities = Set.of(courseEntity);

        when(courseRepository.findCoursesByIdTeacher(userId)).thenReturn(courseEntities);

        ResponseEntity<?> response = coursesService.findCoursesByUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
    }

    @Test
    @Order(12)
    void downloadFile() {
        Long courseId = 1L;
        MultipartFile file = null; // Mock your MultipartFile here

        // Implement the logic to handle file upload tests
    }




}
