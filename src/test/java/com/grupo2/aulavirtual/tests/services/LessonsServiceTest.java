package com.grupo2.aulavirtual.tests.services;

import com.grupo2.aulavirtual.util.files.FileUtil;
import com.grupo2.aulavirtual.util.mappers.DtoMapper;
import com.grupo2.aulavirtual.entities.LessonsEntity;
import com.grupo2.aulavirtual.entities.SubjectsEntity;
import com.grupo2.aulavirtual.payload.request.LessonsDTO;
import com.grupo2.aulavirtual.repositories.LessonsRepository;
import com.grupo2.aulavirtual.repositories.SubjectsRepository;
import com.grupo2.aulavirtual.services.impl.LessonsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LessonsServiceTest {
    @Mock
    private SubjectsRepository subjectsRepository;

    @Mock
    private LessonsRepository lessonsRepository;

    @InjectMocks
    private LessonsServiceImpl lessonsService = new LessonsServiceImpl();

    @Mock
    FileUtil fileUtil = new FileUtil();

    private LessonsDTO lessonsDTO;
    private SubjectsEntity subjectsEntity;
    private LessonsEntity lessonsEntity;
    MockMultipartFile mockFile;

    private String lessonsFolder = "/path/to/lessons/";

    private static final String NOT_FOUND = "No encontrado";
    private static final String SAVE = "data";
    private static final String ERROR = "error";
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field lessonsFolderField = LessonsServiceImpl.class.getDeclaredField("lessonsFolder");
        lessonsFolderField.setAccessible(true);
        lessonsFolderField.set(lessonsService, lessonsFolder);
        MockitoAnnotations.openMocks(this);
        DtoMapper mapper = new DtoMapper();
        lessonsDTO = LessonsDTO.builder()
                .idLesson(1L)
                .name("Nueva lección")
                .description("Descripción de la nueva lección")
                .build();

        subjectsEntity = new SubjectsEntity();
        subjectsEntity.setIdSubject(1L);

        lessonsEntity = mapper.dtoToEntity(lessonsDTO);
        mockFile = new MockMultipartFile("data", "filename.kml", "text/plain", "some kml".getBytes());
    }

    @Test
    void lessonsListEmpty() {
        // Configurar el comportamiento del repositorio de lecciones
        when(lessonsRepository.findAll()).thenReturn(Collections.emptyList());

        // Ejecutar el método bajo prueba
        ResponseEntity<?> response = lessonsService.lessonsList();

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ERROR, response.getBody());
    }

    @Test
    void postLessons() {
        // Configurar el comportamiento del repositorio de temas
        when(subjectsRepository.findById(1L)).thenReturn(Optional.of(subjectsEntity));

        // Configurar el comportamiento del repositorio de lecciones
        when(lessonsRepository.save(any(LessonsEntity.class))).thenReturn(lessonsEntity);

        // Ejecutar el método bajo prueba
        
        ResponseEntity<HashMap<String, ?>> response = lessonsService.postLessons(1L, lessonsDTO);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey(SAVE));
    }




    @Test
    void findLessonsById() {
        // Configurar el comportamiento del repositorio de lecciones
        when(lessonsRepository.existsById(1L)).thenReturn(true);
        when(lessonsRepository.findById(1L)).thenReturn(Optional.of(lessonsEntity));

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> response = lessonsService.findLessonsById(1L);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar que se recibió la respuesta esperada
        assertTrue(response.getBody().containsKey(SAVE));
    }

    @Test
    void lessonIdNotFound() {
        // Configurar el comportamiento del repositorio de lecciones
        when(lessonsRepository.existsById(2L)).thenReturn(false);

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> responseUpdate = lessonsService.updateLesson(2L, lessonsDTO);
        ResponseEntity<HashMap<String, ?>> responseFind = lessonsService.findLessonsById(2L);
        ResponseEntity<HashMap<String, ?>> responseDelete = lessonsService.deleteLesson(2L);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, responseUpdate.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, responseFind.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, responseDelete.getStatusCode());

        // Verificar los mensajes de error
        assertEquals(2L, responseUpdate.getBody().get(ERROR));
        assertEquals(2L, responseFind.getBody().get(ERROR));
        assertEquals(2L, responseDelete.getBody().get(ERROR));
    }


    @Test
    void catchError() {
        // Configurar el comportamiento del repositorio de lecciones
        when(lessonsRepository.save(any())).thenThrow(new RuntimeException("Error simulado"));
        when(lessonsRepository.existsById(any())).thenThrow(new RuntimeException("Error simulado"));

        // Ejecutar el método bajo prueba
        ResponseEntity<HashMap<String, ?>> responseUpdate = lessonsService.updateLesson(1L, lessonsDTO);
        ResponseEntity<HashMap<String, ?>> responseFind = lessonsService.findLessonsById(1L);
        ResponseEntity<HashMap<String, ?>> responseDelete = lessonsService.deleteLesson(1L);

        // Verificar que se recibe una respuesta con el código de estado HttpStatus.INTERNAL_SERVER_ERROR
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseUpdate.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseFind.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseDelete.getStatusCode());

        // Verificar los mensajes de error
        assertEquals("Error simulado", responseUpdate.getBody().get(ERROR));
        assertEquals("Error simulado", responseFind.getBody().get(ERROR));
        assertEquals("Error simulado", responseDelete.getBody().get(ERROR));
    }

    @Test
    void sendFile_Lesson_NotFound() {
        // Arrange
        LessonsEntity lessonEntity = new LessonsEntity();
        lessonEntity.setIdLesson(1L);
        lessonEntity.setName("Lesson 1");
        lessonEntity.setContenido("Content 1");
        lessonEntity.setDescription("Description 1");

        when(lessonsRepository.findById(lessonEntity.getIdLesson())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = lessonsService.sendFile(lessonEntity.getIdLesson());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("error", response.getBody());
    }

    @Test
    void sendFile_Lesson_Content_NotFound() {
        // Arrange
        LessonsEntity lessonEntity = new LessonsEntity();
        lessonEntity.setIdLesson(1L);
        lessonEntity.setName("Lesson 1");
        lessonEntity.setContenido(null);
        lessonEntity.setDescription("Description 1");

        when(lessonsRepository.findById(lessonEntity.getIdLesson())).thenReturn(Optional.of(lessonEntity));

        // Act
        ResponseEntity<?> response = lessonsService.sendFile(lessonEntity.getIdLesson());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("error", response.getBody());
    }

    @Test
    void sendFile_Lesson_Content_File_Found() {
        // Arrange
        LessonsEntity lessonEntity = new LessonsEntity();
        lessonEntity.setIdLesson(1L);
        lessonEntity.setName("Lesson 1");
        lessonEntity.setContenido("Content.pdf");
        lessonEntity.setDescription("Description 1");
        byte[] fileContent = "fileContent".getBytes();

        when(lessonsRepository.findById(lessonEntity.getIdLesson())).thenReturn(Optional.of(lessonEntity));
        when(fileUtil.getExtensionByPath(anyString())).thenReturn("pdf");
        when(fileUtil.getMediaType(anyString())).thenReturn("application/pdf");
        when(fileUtil.sendFile(anyString())).thenReturn(fileContent);

        // Act
        ResponseEntity<?> response = lessonsService.sendFile(lessonEntity.getIdLesson());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_PDF, response.getHeaders().getContentType());
        assertEquals(fileContent, response.getBody());
    }

    @Test
    void sendFile_Lesson_Content_File_NotFound() {
        // Arrange
        LessonsEntity lessonEntity = new LessonsEntity();
        lessonEntity.setIdLesson(1L);
        lessonEntity.setName("Lesson 1");
        lessonEntity.setContenido("Content.pdf");
        lessonEntity.setDescription("Description 1");
        byte[] fileContent = new byte[0];

        when(lessonsRepository.findById(lessonEntity.getIdLesson())).thenReturn(Optional.of(lessonEntity));
        when(fileUtil.getExtensionByPath(anyString())).thenReturn("pdf");
        when(fileUtil.getMediaType(anyString())).thenReturn("application/pdf");
        when(fileUtil.sendFile(anyString())).thenReturn(fileContent);

        // Act
        ResponseEntity<?> response = lessonsService.sendFile(lessonEntity.getIdLesson());
        

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("error", response.getBody());
    }

    @Test
    void downloadFile_FileEmpty_NotFound() {
        // Arrange
        LessonsEntity lessonEntity = new LessonsEntity();
        lessonEntity.setIdLesson(1L);
        lessonEntity.setName("Lesson 1");
        lessonEntity.setContenido("Content.pdf");
        lessonEntity.setDescription("Description 1");
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(true);

        // Act
        ResponseEntity<?> response = lessonsService.downloadFile(lessonEntity.getIdLesson(), file);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("error", response.getBody());
        verify(lessonsRepository, never()).save(any());
    }

    @Test
    void downloadFile_LessonEmpty_NotFound() {
        // Arrange
        LessonsEntity lessonEntity = new LessonsEntity();
        lessonEntity.setIdLesson(1L);
        lessonEntity.setName("Lesson 1");
        lessonEntity.setContenido("Content.pdf");
        lessonEntity.setDescription("Description 1");
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(lessonsRepository.findById(lessonEntity.getIdLesson())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = lessonsService.downloadFile(lessonEntity.getIdLesson(), file);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("error", response.getBody());
        verify(lessonsRepository, never()).save(any());
    }

    @Test
    void downloadFile_saveFile_Success() {
        // Arrange
        String urlFile = "Content.pdf";
        LessonsEntity lessonEntity = new LessonsEntity();
        lessonEntity.setIdLesson(1L);
        lessonEntity.setName("Lesson 1");
        lessonEntity.setContenido(null);
        lessonEntity.setDescription("Description 1");
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(lessonsRepository.findById(lessonEntity.getIdLesson())).thenReturn(Optional.of(lessonEntity));
        when(fileUtil.saveFile(any(MultipartFile.class), anyString())).thenReturn(urlFile);

        // Act
        ResponseEntity<?> response = lessonsService.downloadFile(lessonEntity.getIdLesson(), file);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("data", response.getBody());
        assertEquals(urlFile, lessonEntity.getContenido());
        verify(lessonsRepository, times(1)).save(lessonEntity);
    }

    @Test
    void downloadFile_saveFile_Failure() {
        // Arrange
        LessonsEntity lessonEntity = new LessonsEntity();
        lessonEntity.setIdLesson(1L);
        lessonEntity.setName("Lesson 1");
        lessonEntity.setContenido(null);
        lessonEntity.setDescription("Description 1");
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(lessonsRepository.findById(lessonEntity.getIdLesson())).thenReturn(Optional.of(lessonEntity));
        when(fileUtil.saveFile(any(MultipartFile.class), anyString())).thenReturn(null);

        // Act
        ResponseEntity<?> response = lessonsService.downloadFile(lessonEntity.getIdLesson(), file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("error", response.getBody());
    }

    @Test
    void downloadFile_saveFile_Error() {
        // Arrange
        LessonsEntity lessonEntity = new LessonsEntity();
        lessonEntity.setIdLesson(1L);
        lessonEntity.setName("Lesson 1");
        lessonEntity.setContenido(null);
        lessonEntity.setDescription("Description 1");
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(lessonsRepository.findById(lessonEntity.getIdLesson())).thenReturn(Optional.of(lessonEntity));
        when(fileUtil.saveFile(any(MultipartFile.class), anyString())).thenThrow(new RuntimeException("File saving error"));

        // Act
        ResponseEntity<?> response = lessonsService.downloadFile(lessonEntity.getIdLesson(), file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("File saving error", ((HashMap<String, Object>) response.getBody()).get("error"));
    }

    @Test
    void downloadFile_updateFile_Success() {
        // Arrange
        String newFile = "new Content.pdf";
        LessonsEntity lessonEntity = new LessonsEntity();
        lessonEntity.setIdLesson(1L);
        lessonEntity.setName("Lesson 1");
        lessonEntity.setContenido("Content.pdf");
        lessonEntity.setDescription("Description 1");
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(lessonsRepository.findById(lessonEntity.getIdLesson())).thenReturn(Optional.of(lessonEntity));
        when(fileUtil.updateFile(any(MultipartFile.class), anyString(), anyString())).thenReturn(newFile);

        // Act
        ResponseEntity<?> response = lessonsService.downloadFile(lessonEntity.getIdLesson(), file);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("data", response.getBody());
        assertEquals(newFile, lessonEntity.getContenido());
        verify(lessonsRepository, times(1)).save(lessonEntity);
    }

    @Test
    void downloadFile_updateFile_Failure() {
        // Arrange
        LessonsEntity lessonEntity = new LessonsEntity();
        lessonEntity.setIdLesson(1L);
        lessonEntity.setName("Lesson 1");
        lessonEntity.setContenido("Content.pdf");
        lessonEntity.setDescription("Description 1");
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(lessonsRepository.findById(lessonEntity.getIdLesson())).thenReturn(Optional.of(lessonEntity));
        when(fileUtil.updateFile(any(MultipartFile.class), anyString(), anyString())).thenReturn(null);

        // Act
        ResponseEntity<?> response = lessonsService.downloadFile(lessonEntity.getIdLesson(), file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("error", response.getBody());
    }

    @Test
    void downloadFile_updateFile_Error() {
        // Arrange
        LessonsEntity lessonEntity = new LessonsEntity();
        lessonEntity.setIdLesson(1L);
        lessonEntity.setName("Lesson 1");
        lessonEntity.setContenido("Content.pdf");
        lessonEntity.setDescription("Description 1");
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(lessonsRepository.findById(lessonEntity.getIdLesson())).thenReturn(Optional.of(lessonEntity));
        when(fileUtil.updateFile(any(MultipartFile.class), anyString(), anyString())).thenThrow(new RuntimeException("File saving error"));

        // Act
        ResponseEntity<?> response = lessonsService.downloadFile(lessonEntity.getIdLesson(), file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("File saving error", ((HashMap<String, Object>) response.getBody()).get("error"));
    }
    
}
