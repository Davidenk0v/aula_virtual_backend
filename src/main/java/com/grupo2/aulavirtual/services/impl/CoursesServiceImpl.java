package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.mappers.DtoMapper;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.UserEntity;
import com.grupo2.aulavirtual.payload.request.CourseDTO;
import com.grupo2.aulavirtual.payload.response.CourseResponseDto;
import com.grupo2.aulavirtual.payload.response.UserResponseDto;
import com.grupo2.aulavirtual.repositories.CourseRepository;
import com.grupo2.aulavirtual.repositories.UserRepository;
import com.grupo2.aulavirtual.services.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CoursesServiceImpl implements CourseService {

    @Autowired
    CourseRepository repository;

    @Autowired
    UserRepository userRepository;

    private static final String ERROR = "Error";

    DtoMapper dtoMapper = new DtoMapper();

    Logger logger = LoggerFactory.getLogger(CoursesServiceImpl.class);

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public ResponseEntity<?> courseList() {
        List<CourseEntity> courseEntities = courseRepository.findAll();
        if (courseEntities.isEmpty()) {
            return new ResponseEntity<>("No se encontraron cursos", HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>("No se encontraron cursos", HttpStatus.NOT_FOUND);
        }
        List<CourseResponseDto> courseResponseDtos = coursesPage.stream()
                .map(courseEntity -> dtoMapper.entityToResponseDto(courseEntity)).toList();
        return new ResponseEntity<>(courseResponseDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HashMap<String, ?>> postCourse(String emailUser, CourseDTO courseDTO) {
        try {
            HashMap<String, UserResponseDto> response = new HashMap<>();
            Optional<UserEntity> userOptional = userRepository.findByEmail(emailUser);
            if (userOptional.isPresent()) {
                logger.info("Usuario encontrado");
                UserEntity user = userOptional.get();
                CourseEntity course = dtoMapper.dtoToEntity(courseDTO);
                logger.info("Curso mapeado");
                if (user.getCourses() == null) {
                    ArrayList<CourseEntity> lista = new ArrayList<>();
                    lista.add(course);
                    user.setCourses(lista);
                    logger.info("Lista de cursos creada");
                } else {
                    List<CourseEntity> listaExist = user.getCourses();
                    listaExist.add(course);
                    user.setCourses(listaExist);
                    logger.info("Añadido a la lista");
                }
                userRepository.save(user);
                logger.info("Usuario guardado");
                UserResponseDto objectResponse = dtoMapper.entityToResponseDto(user);
                logger.info("Usuario respuesta mapeado");
                courseRepository.save(course);
                response.put("Curso subido", objectResponse);
                return ResponseEntity.status(201).body(response);
            } else {
                HashMap<String, String> error = new HashMap<>();
                error.put("No ha encontrado el usuario: ", emailUser);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
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
                    // Eliminar las relaciones del curso con los usuarios
                    List<UserEntity> users = course.getUser();
                    if (users != null && !users.isEmpty()) {
                        for (UserEntity user : users) {
                            user.getCourses().remove(course);
                            userRepository.save(user);
                        }
                    }

                    // Eliminar el curso
                    courseRepository.delete(course);

                    response.put("message", "El curso y sus relaciones han sido eliminados exitosamente.");
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
            error.put(ERROR, "Error al intentar eliminar el curso y sus relaciones: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @Override
    public ResponseEntity<HashMap<String, ?>> updateCourse(Long id, CourseDTO courseDTO) {
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
                response.put("Se ha modificado correctamente", dtoMapper.entityToResponseDto(course));
                return ResponseEntity.status(200).body(response);
            } else {
                HashMap<String, Long> error = new HashMap<>();
                error.put("No ha encontrado el curso con id: ", id);
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
                logger.info(course.toString());
                logger.info(dtoMapper.entityToResponseDto(course).toString());
                return ResponseEntity.status(200).body(dtoMapper.entityToResponseDto(course));
            } else {
                HashMap<String, Long> error = new HashMap<>();
                error.put("No ha encontrado el curso con id: ", id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
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
                response.put("Cursos", courseResponseDtos);
                return ResponseEntity.status(201).body(response);
            } else {
                HashMap<String, String> errorNotFound = new HashMap<>();
                errorNotFound.put("Ningun curso con:", name);
                return ResponseEntity.status(500).body(errorNotFound);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }
}
