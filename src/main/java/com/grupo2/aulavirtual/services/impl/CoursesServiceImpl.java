package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.entities.CategoryEntity;
import com.grupo2.aulavirtual.util.mappers.DtoMapper;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.payload.request.CourseDTO;
import com.grupo2.aulavirtual.payload.response.CourseResponseDto;
import com.grupo2.aulavirtual.repositories.CategoryRepository;
import com.grupo2.aulavirtual.repositories.CourseRepository;
import com.grupo2.aulavirtual.services.CourseService;
import com.grupo2.aulavirtual.services.KeycloakService;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.grupo2.aulavirtual.util.files.FileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CoursesServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private KeycloakService keycloakService;

    private static final String ERROR = "error";
    private static final String SAVE = "data";
    DtoMapper dtoMapper = new DtoMapper();
    FileUtil fileUtil = new FileUtil();

    Logger logger = LoggerFactory.getLogger(CoursesServiceImpl.class);

    @Value("${fileutil.default.img.course}")
    private String defaultImg;
    @Value("${fileutil.course.folder.path}")
    private String courseFolder;

    @Override
    public ResponseEntity<?> courseList() {
        List<CourseEntity> courseEntities = courseRepository.findAll();
        if (courseEntities.isEmpty()) {
            return new ResponseEntity<>(ERROR, HttpStatus.NOT_FOUND);
        }
        List<CourseResponseDto> courseResponseDtos = courseEntities.stream()
                .map(courseEntity -> dtoMapper.entityToResponseDto(courseEntity)).toList();
        return new ResponseEntity<>(courseResponseDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> pageableCourseList(@NonNull Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 3, pageable.getSort());
        Page<CourseEntity> coursesPage = this.courseRepository.findAll(pageable);
        if (coursesPage.isEmpty()) {
            return new ResponseEntity<>(ERROR, HttpStatus.NOT_FOUND);
        }
        List<CourseResponseDto> courseResponseDtos = coursesPage.stream()
                .map(courseEntity -> dtoMapper.entityToResponseDto(courseEntity)).toList();
        return new ResponseEntity<>(courseResponseDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> postCourse(String idKeycloak, CourseDTO courseDTO) {
        try {
            HashMap<String, CourseResponseDto> response = new HashMap<>();
            UserRepresentation user = keycloakService.findUserById(idKeycloak);
            if (user != null) {
                CourseEntity course = dtoMapper.dtoToEntity(courseDTO);
                course.setIdTeacher(idKeycloak);
                course.getIdCourse();
                course.setCreatedDate(LocalDateTime.now());
                course.setLastModifiedDate(LocalDateTime.now());
                course.setUrlImg(defaultImg);
                CourseResponseDto responseDto = dtoMapper.entityToResponseDto(course);
                courseRepository.save(course);
                response.put(SAVE, responseDto);
                return ResponseEntity.status(201).body(course);
            } else {
                HashMap<String, String> error = new HashMap<>();
                error.put(ERROR, idKeycloak);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }

    }

    /**
     * Metodo para distinguir entre nuevo archivo o actualizar archivo.
     * 
     * @param id   Long con la id de Lessons.
     * @param file MultiparFile con los datos del archivo.
     * @return ResponseEntity<?> con el estado de la operacion.
     */
    public ResponseEntity<?> downloadFile(Long id, MultipartFile file) {
        if (!file.isEmpty()) {
            Optional<CourseEntity> optionalCourse = courseRepository.findById(id);
            if (optionalCourse.isPresent()) {
                CourseEntity course = optionalCourse.get();
                if (course.getUrlImg() == null || course.getUrlImg().isEmpty()) {
                    return saveFile(course, file);
                } else {
                    return updateFile(course, file);
                }
            } else {
                return new ResponseEntity<>(ERROR, HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(ERROR, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Metodo para guardar un archivo.
     * 
     * @param course UserEntity a la que se le hacen los cambios.
     * @param file   MultiparFile con los datos del archivo.
     * @return ResponseEntity<?> con el estado de la operacion.
     */
    public ResponseEntity<?> saveFile(CourseEntity course, MultipartFile file) {
        try {
            String path = fileUtil.saveFile(file, getCustomPath(course.getName()));
            course.setUrlImg(path);
            courseRepository.save(course);
            if (path != null) {
                return new ResponseEntity<>(SAVE, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    /**
     * Metodo para sobreescribir un archivo.
     * 
     * @param course UserEntity a la que se le hacen los cambios.
     * @param file   MultiparFile con los datos del archivo.
     * @return ResponseEntity<?> con el estado de la operacion.
     */
    public ResponseEntity<?> updateFile(CourseEntity course, MultipartFile file) {
        try {
            String path = fileUtil.updateFile(file, getCustomPath(course.getName()),
            getCustomPath(course.getName()) + course.getUrlImg(), defaultImg);
            course.setUrlImg(path);
            courseRepository.save(course);
            if (path != null) {
                return new ResponseEntity<>(SAVE, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    @Override
    public ResponseEntity<?> deleteCourse(Long id) {
        try {
            HashMap<String, Object> response = new HashMap<>();
            if (courseRepository.existsById(id)) {
                CourseEntity course = courseRepository.findById(id).orElse(null);
                if (course != null) {

                    // Eliminar el curso
                    courseRepository.delete(course);

                    response.put(SAVE, "El curso y sus relaciones han sido eliminados exitosamente.");
                    return ResponseEntity.status(200).body(response);
                } else {
                    response.put(ERROR, "No se encontró el curso con ID: " + id);
                    return ResponseEntity.status(404).body(response);
                }
            } else {
                response.put(ERROR, "No se encontró el curso con ID: " + id);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            HashMap<String, Object> error = new HashMap<>();
            error.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    public ResponseEntity<?> setDefaultImage(Long id) {
        try {
            Optional<CourseEntity> optionalCourse = courseRepository.findById(id);
            if (optionalCourse.isPresent()) {
                CourseEntity course = optionalCourse.get();
                course.setLastModifiedDate(LocalDateTime.now());
                if (course.getUrlImg() != null && !course.getUrlImg().isEmpty()) {
                    fileUtil.deleteFile(getCustomPath(course.getName()) + course.getUrlImg(), defaultImg);
                    course.setUrlImg(defaultImg);
                    courseRepository.save(course);
                } else {
                    course.setUrlImg(defaultImg);
                    courseRepository.save(course);
                }
                return new ResponseEntity<>(SAVE, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ERROR, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            HashMap<String, Object> error = new HashMap<>();
            error.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(error);
        }

    }

    @Override
    public ResponseEntity<HashMap<String, ?>> updateCourse(Long id, CourseDTO courseDTO) {
        logger.info(courseDTO.toString());
        logger.info(courseDTO.toString());
        try {
            HashMap<String, CourseResponseDto> response = new HashMap<>();
            if (courseRepository.existsById(id)) {
                CourseEntity course = courseRepository.findById(id).get();
                if (!Objects.equals(courseDTO.getName(), "")) {
                    course.setName(courseDTO.getName());
                }
                if (!Objects.equals(courseDTO.getDescription(), "")) {
                    course.setDescription(courseDTO.getDescription());
                }
                courseRepository.save(course);
                response.put(SAVE, dtoMapper.entityToResponseDto(course));
                return ResponseEntity.status(200).body(response);
            } else {
                HashMap<String, Long> error = new HashMap<>();
                error.put(ERROR, id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }

    }

    @Override
    public ResponseEntity<?> findCourseById(Long id) {
        try {
            if (courseRepository.findById(id).isPresent()) {
                CourseEntity course = courseRepository.findById(id).get();
                return ResponseEntity.status(200).body(dtoMapper.entityToResponseDto(course));
            } else {
                HashMap<String, Long> error = new HashMap<>();
                error.put(ERROR, id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    @Override
    public ResponseEntity<?> findCoursesByCategory(String category) {
        try {
            if (categoryRepository.findByCategoryContaining(category).isPresent()) {
                CategoryEntity categoryEntity = categoryRepository.findByCategoryContaining(category).get();
                Set<CourseEntity> courseEntities = courseRepository.findCoursesByCategory(categoryEntity);
                List<CourseResponseDto> courseResponseDtos = courseEntities.stream()
                        .map(courseEntity -> dtoMapper.entityToResponseDto(courseEntity)).toList();
                return ResponseEntity.status(200).body(courseResponseDtos);
            } else {
                HashMap<String, String> error = new HashMap<>();
                error.put(ERROR, category);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    @Override
    public ResponseEntity<?> findCoursesByUser(String idUser) {
        try {
                Set<CourseEntity> courseEntities = courseRepository.findCoursesByIdTeacher(idUser);
            if (!courseEntities.isEmpty()) {
                List<CourseResponseDto> courseResponseDtos = courseEntities.stream()
                        .map(courseEntity -> dtoMapper.entityToResponseDto(courseEntity)).toList();
                return ResponseEntity.status(200).body(courseResponseDtos);
            } else {
                HashMap<String, String> error = new HashMap<>();
                error.put(ERROR, idUser) ;
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    /**
     * Metodo para enviar un archivo al frontend.
     * 
     * @param id Long con la id del curso.
     * @return ResponseEntity<?> con la imagen, con string en caso de error.
     */
    public ResponseEntity<?> sendFile(Long id) {
        Optional<CourseEntity> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            CourseEntity course = optionalCourse.get();
            if (course.getUrlImg() != null && !course.getUrlImg().isEmpty()) {
                String fileRoute = getCustomPath(course.getName()) + course.getUrlImg();
                String extension = fileUtil.getExtensionByPath(fileRoute);
                String mediaType = fileUtil.getMediaType(extension);
                byte[] file = fileUtil.sendFile(fileRoute, defaultImg);
                if (file.length != 0) {
                    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(mediaType)).body(file);
                } else {
                    return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>(ERROR, HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(ERROR, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<HashMap<String, ?>> findAllByContains(String name) {
        try {

            if (courseRepository.findByKeyword(name).isPresent()) {
                HashMap<String, List<CourseResponseDto>> response = new HashMap<>();
                List<CourseEntity> courseEntities = courseRepository.findByKeyword(name).get();
                List<CourseResponseDto> courseResponseDtos = courseEntities.stream()
                        .map(courseEntity -> dtoMapper.entityToResponseDto(courseEntity)).toList();
                response.put(SAVE, courseResponseDtos);
                return ResponseEntity.status(201).body(response);
            } else {
                HashMap<String, String> errorNotFound = new HashMap<>();
                errorNotFound.put(ERROR, name);
                return ResponseEntity.status(500).body(errorNotFound);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    private String getCustomPath(String courseNane) {
        return courseFolder + courseNane + "\\Image\\";
    }

}
