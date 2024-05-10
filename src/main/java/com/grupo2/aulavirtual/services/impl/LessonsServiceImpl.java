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
import com.grupo2.aulavirtual.util.FileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Service
public class LessonsServiceImpl implements LessonsService {

    @Autowired
    SubjectsRepository repository;

    @Autowired
    LessonsRepository lessonsRepository;

    DtoMapper dtoMapper = new DtoMapper();
    FileUtil fileUtil = new FileUtil();

    @Override
    public ResponseEntity<?> lessonsList() {
        List<LessonsEntity> lessonsEntities = lessonsRepository.findAll();
        if (lessonsEntities.isEmpty()) {
            return new ResponseEntity<>("No se encontraron lecciones", HttpStatus.NOT_FOUND);
        }
        List<LessonsResponseDto> lessonsResponseDtos = lessonsEntities.stream()
                .map(lessonsEntity -> dtoMapper.entityToResponseDto(lessonsEntity)).toList();
        return new ResponseEntity<>(lessonsResponseDtos, HttpStatus.OK);
    }

    public ResponseEntity<?> sendFile(Long id) {
        // Encontrar usuario por id
        LessonsEntity lessons = lessonsRepository.findById(id).get();
        // verificar si ya tiene un archivo asociado
        if (lessons.getContenido().isEmpty()) {
            // recoger achivo
            String fileRoute = lessons.getContenido();
            String extension = fileUtil.getExtensionByPath(fileRoute);
            String medoaType = fileUtil.getMediaType(extension);
            byte[] file = fileUtil.sendFile(fileRoute);
            // enviar archivo.
            if (file.length != 0) {
                return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(medoaType)).body(file);
            } else {
                return new ResponseEntity<>("Ocurrio un error, el archivo puede estar corrupto.",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            // error en caso negativo
        } else {
            return new ResponseEntity<>("No se encuentra el archivo.",
                        HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<HashMap<String, ?>> postLessons(Long idSubject, LessonsDTO lessonsDTO, MultipartFile file) {
        try {
            HashMap<String, SubjectsResponseDto> response = new HashMap<>();
            SubjectsEntity subjects = repository.findById(idSubject).get();
            LessonsEntity lessons = new LessonsEntity();
            lessons = dtoMapper.dtoToEntity(lessonsDTO);
            lessons.setSubject(subjects);
            if (!file.isEmpty()) {
                String path = fileUtil.saveFile(file, "\\Lessons\\" + lessonsDTO.getName() + "\\files");
                lessons.setContenido(path);
            }
            SubjectsResponseDto objectResponse = dtoMapper.entityToResponseDto(subjects);
            lessonsRepository.save(lessons);
            response.put("Leccion subido", objectResponse);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }

    }

    public ResponseEntity<?> saveFile(Long id, MultipartFile file) {
        if (!file.isEmpty()) {
            // Encontrar usuario por id
            LessonsEntity lessons = lessonsRepository.findById(id).get();
            // verificar si ya tiene un archivo asociado.
            if (lessons.getContenido().isEmpty()) {
                // Guardar achivo
                String path = fileUtil.saveFile(file, "\\Lessons\\" + lessons.getName() + "\\files");
                // Recoger ruta.
                lessons.setContenido(path);
                // guardar en BBDD
                lessonsRepository.save(lessons);
                // enviar respuesta
                if (path != null) {
                    return new ResponseEntity<>("Se ha añadido el archivo", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(
                            "Ocurrio un error al almacenar el archivo", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                // Sobreescribir achivo
                String path = fileUtil.updateFile(file, lessons.getContenido());
                // enviar respuesta
                if (path != null) {
                    return new ResponseEntity<>("Se ha añadido el archivo", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(
                            "Ocurrio un error al almacenar el archivo", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            return new ResponseEntity<>("No se ha enviado ningun archivo", HttpStatus.NOT_ACCEPTABLE);
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
    public ResponseEntity<HashMap<String, ?>> updateLesson(Long id, LessonsDTO lessonsDTO, MultipartFile file) {
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
                if (!file.isEmpty()) {
                    String path = fileUtil.updateFile(file, subject.getContenido());
                    subject.setContenido(path);
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
