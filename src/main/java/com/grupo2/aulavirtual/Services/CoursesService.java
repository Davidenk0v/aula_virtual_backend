package com.grupo2.aulavirtual.Services;


import com.grupo2.aulavirtual.Config.Mappers.DtoMapper;
import com.grupo2.aulavirtual.Entities.CourseEntity;
import com.grupo2.aulavirtual.Entities.UserEntity;
import com.grupo2.aulavirtual.Payload.Request.CourseDTO;
import com.grupo2.aulavirtual.Payload.Response.CourseResponseDto;
import com.grupo2.aulavirtual.Payload.Response.UserResponseDto;
import com.grupo2.aulavirtual.Repository.CourseRepository;
import com.grupo2.aulavirtual.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CoursesService {


    @Autowired
    CourseRepository repository;

    @Autowired
    UserRepository userRepository;


    DtoMapper dtoMapper = new DtoMapper();
    @Autowired
    private CourseRepository courseRepository;

    public ResponseEntity<HashMap<String , ?>> postCourse (String email,CourseDTO courseDTO) {
        try {
            HashMap<String , UserResponseDto> response = new HashMap<>();
            UserEntity  user  =  userRepository.findByEmail(email).get();
            CourseEntity course = new CourseEntity();
            course = dtoMapper.dtoToEntity(courseDTO);
            if (user.getCourses()==null){
                ArrayList<CourseEntity> lista = new ArrayList<>();
                lista.add(course);
                user.setCourses(lista);
            }else {
                List<CourseEntity> listaExist = user.getCourses();
                listaExist.add(course);
                user.setCourses(listaExist);
            }
            UserResponseDto objectResponse =  dtoMapper.entityToResponseDto(user);
            userRepository.save(user);
            response.put("Curso subido " , objectResponse);
            return ResponseEntity.status(201).body(response);
        }catch (Exception e){
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error",e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }

    }

    public ResponseEntity<HashMap<String , ?>> deleteCourse (Long id) {
        try {
            HashMap<String , CourseResponseDto> response = new HashMap<>();
            if (courseRepository.existsById(id)){
                CourseEntity course  = courseRepository.findById(id).get();
                courseRepository.delete(course);
                response.put("Se ha borrado el curso ", dtoMapper.entityToResponseDto(course));
                return ResponseEntity.status(200).body(response);
            }else {
                HashMap<String , Long> error = new HashMap<>();
                error.put("No ha encontrado el usuario con id: " , id);
                return ResponseEntity.status(500).body(error);
            }
        }catch (Exception e){
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error",e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }

    }

    public ResponseEntity<HashMap<String , ?>> updateCourse (Long id,CourseDTO courseDTO) {
        try {
            HashMap<String , CourseResponseDto> response = new HashMap<>();
            if (courseRepository.existsById(id)){
                CourseEntity course  = courseRepository.findById(id).get();
                if (courseDTO.getName()!=""){
                    course.setName(courseDTO.getName());
                }
                if (courseDTO.getDescription()!=""){
                    course.setDescription(courseDTO.getDescription());
                }
                courseRepository.save(course);
                response.put("Se ha modificado correctamente", dtoMapper.entityToResponseDto(course));
                return ResponseEntity.status(200).body(response);
            }else {
                HashMap<String , Long> error = new HashMap<>();
                error.put("No ha encontrado el usuario con id: " , id);
                return ResponseEntity.status(500).body(error);
            }
        }catch (Exception e){
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error",e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }

    }
}
