package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.mappers.DtoMapper;
import com.grupo2.aulavirtual.entities.LessonsEntity;
import com.grupo2.aulavirtual.entities.SubjectsEntity;
import com.grupo2.aulavirtual.payload.request.LessonsDTO;
import com.grupo2.aulavirtual.payload.response.LessonsResponseDto;
import com.grupo2.aulavirtual.payload.response.SubjectsResponseDto;
import com.grupo2.aulavirtual.repositories.LessonsRepository;
import com.grupo2.aulavirtual.repositories.SubjectsRepository;
import com.grupo2.aulavirtual.services.LessonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LessonsServiceImpl implements LessonsService {

    @Autowired
    SubjectsRepository repository;


    @Autowired
    LessonsRepository lessonsRepository;

    DtoMapper dtoMapper = new DtoMapper();


    @Override
    public ResponseEntity<?> lessonsList() {
        List<LessonsEntity> lessonsEntities = lessonsRepository.findAll();
        if(lessonsEntities.isEmpty()){
            return new ResponseEntity<>("No se encontraron lecciones", HttpStatus.NOT_FOUND);
        }
        List<LessonsResponseDto> lessonsResponseDtos = lessonsEntities.stream().map(lessonsEntity -> dtoMapper.entityToResponseDto(lessonsEntity)).toList();
        return new ResponseEntity<>(lessonsResponseDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HashMap<String, ?>> postLessons(Long idSubject, LessonsDTO lessonsDTO) {
        try {
            HashMap<String, SubjectsResponseDto> response = new HashMap<>();
            SubjectsEntity subjects = repository.findById(idSubject).get();
            LessonsEntity lessons = new LessonsEntity();
            lessons = dtoMapper.dtoToEntity(lessonsDTO);
            lessons.setSubject(subjects);
            SubjectsResponseDto objectResponse = dtoMapper.entityToResponseDto(subjects);
            lessonsRepository.save(lessons);
            response.put("Tema subido ", objectResponse);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }

    }

    @Override
    public ResponseEntity<HashMap<String, ?>> deleteLesson(Long id) {
        try {
            HashMap<String, LessonsResponseDto> response = new HashMap<>();
            if (lessonsRepository.existsById(id)) {
                LessonsEntity lessons = lessonsRepository.findById(id).get();
                lessonsRepository.delete(lessons);
                response.put("Se ha borrado el tema ", dtoMapper.entityToResponseDto(lessons));
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
    public ResponseEntity<HashMap<String, ?>> updateLesson(Long id, LessonsDTO lessonsDTO) {
        try {
            HashMap<String, LessonsResponseDto> response = new HashMap<>();
            if (lessonsRepository.existsById(id)) {
                LessonsEntity subject = lessonsRepository.findById(id).get();
                if (lessonsDTO.getName() != "") {
                    subject.setName(lessonsDTO.getName());
                }
                if (lessonsDTO.getDescription() != "") {
                    subject.setDescription(lessonsDTO.getDescription());
                }
                lessonsRepository.save(subject);
                response.put("Se ha modificado correctamente", dtoMapper.entityToResponseDto(subject));
                return ResponseEntity.status(200).body(response);
            } else {
                HashMap<String, Long> error = new HashMap<>();
                error.put("No ha encontrado la leccion con id: ", id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }

    }

    @Override
    public ResponseEntity<HashMap<String, ?>> findLessonsById(Long id) {
        try {
            HashMap<String, LessonsResponseDto> response = new HashMap<>();
            if (lessonsRepository.existsById(id)) {
                LessonsEntity lessons = lessonsRepository.findById(id).get();
                response.put("Id encontrado ", dtoMapper.entityToResponseDto(lessons));
                return ResponseEntity.status(200).body(response);
            } else {
                HashMap<String, Long> error = new HashMap<>();
                error.put("No ha encontrado la leccion con id: ", id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }
}
