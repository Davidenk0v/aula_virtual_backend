package com.grupo2.aulavirtual.Services;

import com.grupo2.aulavirtual.Config.Mappers.DtoMapper;
import com.grupo2.aulavirtual.Entities.CourseEntity;
import com.grupo2.aulavirtual.Entities.SubjectsEntity;
import com.grupo2.aulavirtual.Entities.UserEntity;
import com.grupo2.aulavirtual.Payload.Request.CourseDTO;
import com.grupo2.aulavirtual.Payload.Request.SubjectDTO;
import com.grupo2.aulavirtual.Payload.Response.CourseResponseDto;
import com.grupo2.aulavirtual.Payload.Response.SubjectsResponseDto;
import com.grupo2.aulavirtual.Payload.Response.UserResponseDto;
import com.grupo2.aulavirtual.Repository.CourseRepository;
import com.grupo2.aulavirtual.Repository.SubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SubjectsService {

    @Autowired
    CourseRepository repository;


    @Autowired
    SubjectsRepository repositorySubjects;

    DtoMapper dtoMapper = new DtoMapper();


    public ResponseEntity<?> subjectsList() {
        List<SubjectsEntity> subjectsEntities = repositorySubjects.findAll();
        if(subjectsEntities.isEmpty()){
            return new ResponseEntity<>("No se encontraron temas", HttpStatus.NOT_FOUND);
        }
        List<SubjectsResponseDto> courseResponseDtos = subjectsEntities.stream().map(subjectEntity -> dtoMapper.entityToResponseDto(subjectEntity)).toList();
        return new ResponseEntity<>(courseResponseDtos, HttpStatus.OK);
    }

    public ResponseEntity<HashMap<String, ?>> postSubject(Long idCourse, SubjectDTO subjectDTO) {
            HashMap<String, CourseResponseDto> response = new HashMap<>();
            CourseEntity course = repository.findById(idCourse).get();
            SubjectsEntity subject = new SubjectsEntity();
            subject = dtoMapper.dtoToEntity(subjectDTO);
            subject.setCourse(course);
            repositorySubjects.save(subject);
            CourseResponseDto objectResponse = dtoMapper.entityToResponseDto(course);
            repository.save(course);
            response.put("Tema subido ", objectResponse);
            return ResponseEntity.status(201).body(response);

    }

    public ResponseEntity<HashMap<String, ?>> deleteSubject(Long id) {
        try {
            HashMap<String, SubjectsResponseDto> response = new HashMap<>();
            if (repositorySubjects.existsById(id)) {
                SubjectsEntity subject = repositorySubjects.findById(id).get();
                repositorySubjects.delete(subject);
                response.put("Se ha borrado el tema ", dtoMapper.entityToResponseDto(subject));
                return ResponseEntity.status(200).body(response);
            } else {
                HashMap<String, Long> error = new HashMap<>();
                error.put("No ha encontrado el tema con id: ", id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }

    }

    public ResponseEntity<HashMap<String, ?>> updateSubject(Long id, SubjectDTO subjectDTO) {
        try {
            HashMap<String, SubjectsResponseDto> response = new HashMap<>();
            if (repositorySubjects.existsById(id)) {
                SubjectsEntity subject = repositorySubjects.findById(id).get();
                if (subjectDTO.getName() != "") {
                    subject.setName(subjectDTO.getName());
                }
                if (subjectDTO.getDescription() != "") {
                    subject.setDescription(subjectDTO.getDescription());
                }
                repositorySubjects.save(subject);
                response.put("Se ha modificado correctamente", dtoMapper.entityToResponseDto(subject));
                return ResponseEntity.status(200).body(response);
            } else {
                HashMap<String, Long> error = new HashMap<>();
                error.put("No ha encontrado el tema con id: ", id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }

    }


    public ResponseEntity<HashMap<String, ?>> findSubjectById(Long id) {
        try {
            HashMap<String, SubjectsResponseDto> response = new HashMap<>();
            if (repositorySubjects.existsById(id)) {
                SubjectsEntity subject = repositorySubjects.findById(id).get();
                response.put("Se ha encontrado el tema ", dtoMapper.entityToResponseDto(subject));
                return ResponseEntity.status(200).body(response);
            } else {
                HashMap<String, Long> error = new HashMap<>();
                error.put("No ha encontrado el tema con id: ", id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

}
