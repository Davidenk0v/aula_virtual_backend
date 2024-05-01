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

@Service
public class CoursesServiceImpl implements CourseService {

    @Autowired
    CourseRepository repository;

    @Autowired
    UserRepository userRepository;

    DtoMapper dtoMapper = new DtoMapper();

    @Autowired
    private CourseRepository courseRepository;


    @Override
    public ResponseEntity<?> courseList() {
        List<CourseEntity> courseEntities = courseRepository.findAll();
        if(courseEntities.isEmpty()){
            return new ResponseEntity<>("No se encontraron cursos", HttpStatus.NOT_FOUND);
        }
        List<CourseResponseDto> courseResponseDtos = courseEntities.stream().map(courseEntity -> dtoMapper.entityToResponseDto(courseEntity)).toList();
        return new ResponseEntity<>(courseResponseDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> pageableCourseList(@NonNull Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 3, pageable.getSort());
        Page<CourseEntity> coursesPage = this.courseRepository.findAll(pageable);
        if(coursesPage.isEmpty()){
            return new ResponseEntity<>("No se encontraron cursos", HttpStatus.NOT_FOUND);
        }
        List<CourseResponseDto> courseResponseDtos = coursesPage.stream().map(courseEntity -> dtoMapper.entityToResponseDto(courseEntity)).toList();
        return new ResponseEntity<>(courseResponseDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HashMap<String, ?>> postCourse(Long idUser, CourseDTO courseDTO) {
        try {
            HashMap<String, UserResponseDto> response = new HashMap<>();
            UserEntity user = userRepository.findById(idUser).get();
            CourseEntity course = new CourseEntity();
            course = dtoMapper.dtoToEntity(courseDTO);
            if (user.getCourses() == null) {
                ArrayList<CourseEntity> lista = new ArrayList<>();
                lista.add(course);
                user.setCourses(lista);
            } else {
                List<CourseEntity> listaExist = user.getCourses();
                listaExist.add(course);
                user.setCourses(listaExist);
            }
            UserResponseDto objectResponse = dtoMapper.entityToResponseDto(user);
            userRepository.save(user);
            response.put("Curso subido ", objectResponse);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }

    }

    @Override
    public ResponseEntity<?> deleteCourse(Long id) {
        try {
            HashMap<String, String> response = new HashMap<>();
            if (courseRepository.existsById(id)) {
                CourseEntity course = courseRepository.findById(id).get();
                courseRepository.delete(course);
                response.put("Ok","Se ha eliminado correctamente");
                return ResponseEntity.status(200).body(response);
            } else {
                HashMap<String, Long> error = new HashMap<>();
                error.put("No ha encontrado el curso con id: ", id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }

    }

    @Override
    public ResponseEntity<HashMap<String, ?>> updateCourse(Long id, CourseDTO courseDTO) {
        try {
            HashMap<String, CourseResponseDto> response = new HashMap<>();
            if (courseRepository.existsById(id)) {
                CourseEntity course = courseRepository.findById(id).get();
                if (courseDTO.getName() != "") {
                    course.setName(courseDTO.getName());
                }
                if (courseDTO.getDescription() != "") {
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
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }

    }

    @Override
    public ResponseEntity<?> findCourseById(Long id) {
        try {
            HashMap<String, CourseResponseDto> response = new HashMap<>();
            if (courseRepository.existsById(id)) {
                CourseEntity course = courseRepository.findById(id).get();
                return ResponseEntity.status(200).body(dtoMapper.entityToResponseDto(course));
            } else {
                HashMap<String, Long> error = new HashMap<>();
                error.put("No ha encontrado el curso con id: ", id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }
}
