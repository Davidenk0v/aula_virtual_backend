package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.util.mappers.DtoMapper;
import com.grupo2.aulavirtual.entities.LessonsEntity;
import com.grupo2.aulavirtual.entities.SubjectsEntity;
import com.grupo2.aulavirtual.payload.request.LessonsDTO;
import com.grupo2.aulavirtual.payload.response.LessonsResponseDto;
import com.grupo2.aulavirtual.payload.response.SubjectsResponseDto;
import com.grupo2.aulavirtual.repositories.LessonsRepository;
import com.grupo2.aulavirtual.repositories.SubjectsRepository;
import com.grupo2.aulavirtual.services.LessonsService;
import com.grupo2.aulavirtual.util.files.FileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class LessonsServiceImpl implements LessonsService {

    @Autowired
    SubjectsRepository repository;

    @Autowired
    LessonsRepository lessonsRepository;

    DtoMapper dtoMapper = new DtoMapper();
    FileUtil fileUtil = new FileUtil();
    @Value("${fileutil.lessons.folder.path}")
    private String lessonsFolder;

    private static final String SAVE = "data";
    private static final String ERROR = "error";

    @Override
    public ResponseEntity<?> lessonsList() {
        List<LessonsEntity> lessonsEntities = lessonsRepository.findAll();
        if (lessonsEntities.isEmpty()) {
            return new ResponseEntity<>(ERROR, HttpStatus.NOT_FOUND);
        }
        List<LessonsResponseDto> lessonsResponseDtos = lessonsEntities.stream()
                .map(lessonsEntity -> dtoMapper.entityToResponseDto(lessonsEntity)).toList();
        return new ResponseEntity<>(lessonsResponseDtos, HttpStatus.OK);
    }

    public ResponseEntity<?> getLessonBySubjebtId(Long subjectId) {
        try {
            Optional<SubjectsEntity> subject = repository.findById(subjectId);
            if (!subject.isEmpty()) {
                SubjectsEntity subjectsEntity = subject.get();
                List<LessonsEntity> lista = subjectsEntity.getLessons();
                int size = lista.size();
                LessonsEntity lastAdded = lista.get(size - 1);
                LessonsResponseDto lessonsResponseDto = dtoMapper.entityToResponseDto(lastAdded);
                return ResponseEntity.status(201).body(lessonsResponseDto);
            } else {
                return ResponseEntity.status(404).body("No hay subjects");
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    public ResponseEntity<?> sendFile(Long id) {
        Optional<LessonsEntity> optionalLessons = lessonsRepository.findById(id);
        if (optionalLessons.isPresent()) {
            LessonsEntity lessons = optionalLessons.get();
            if (lessons.getContenido() != null || !lessons.getContenido().isEmpty()) {
                String fileRoute = lessons.getContenido();
                String extension = fileUtil.getExtensionByPath(fileRoute);
                String medoaType = fileUtil.getMediaType(extension);
                byte[] file = fileUtil.sendFile(lessonsFolder + fileRoute);
                if (file.length != 0) {
                    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(medoaType)).body(file);
                } else {
                    return new ResponseEntity<>("Ocurrio un error, el archivo puede estar corrupto.",
                            HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>("No se encontro la leccion.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("No se encuentra el archivo.",
                    HttpStatus.NOT_FOUND);
        }
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
            response.put(SAVE, objectResponse);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
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
            Optional<LessonsEntity> optionalLessons = lessonsRepository.findById(id);
            if (optionalLessons.isPresent()) {
                LessonsEntity lessons = optionalLessons.get();
                if (lessons.getContenido() == null || lessons.getContenido().isEmpty()) {
                    return saveFile(lessons, file);
                } else {
                    return updateFile(lessons, file);
                }
            } else {
                return new ResponseEntity<>("No se encontro la leccion.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("No se ha enviado ningun archivo", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Metodo para guardar un archivo.
     * 
     * @param lessons LessonsEntity a la que se le hacen los cambios.
     * @param file    MultiparFile con los datos del archivo.
     * @return ResponseEntity<?> con el estado de la operacion.
     */
    public ResponseEntity<?> saveFile(LessonsEntity lessons, MultipartFile file) {
        try {
            String path = fileUtil.saveFile(file, lessonsFolder);
            lessons.setContenido(path);
            lessonsRepository.save(lessons);
            if (path != null) {
                return new ResponseEntity<>("Se ha añadido el archivo", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        "Ocurrio un error al almacenar el archivo", HttpStatus.INTERNAL_SERVER_ERROR);
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
     * @param lessons LessonsEntity a la que se le hacen los cambios.
     * @param file    MultiparFile con los datos del archivo.
     * @return ResponseEntity<?> con el estado de la operacion.
     */
    public ResponseEntity<?> updateFile(LessonsEntity lessons, MultipartFile file) {
        try {
            String path = fileUtil.updateFile(file, lessonsFolder,
                    lessonsFolder + lessons.getContenido());
            lessons.setContenido(path);
            lessonsRepository.save(lessons);
            if (path != null) {
                return new ResponseEntity<>("Se ha añadido el archivo", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        "Ocurrio un error al almacenar el archivo", HttpStatus.INTERNAL_SERVER_ERROR);
            }
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
                response.put(SAVE, dtoMapper.entityToResponseDto(lessons));
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
                response.put(SAVE, dtoMapper.entityToResponseDto(subject));
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
    public ResponseEntity<HashMap<String, ?>> findLessonsById(Long id) {
        try {
            HashMap<String, LessonsResponseDto> response = new HashMap<>();
            if (lessonsRepository.existsById(id)) {
                LessonsEntity lessons = lessonsRepository.findById(id).get();
                // Hay problemas en el dtomapper devuelve 500
                // response.put(SAVE, dtoMapper.entityToResponseDto(lessons));
                HashMap<String, String> responseTest = new HashMap<>();
                responseTest.put(SAVE, "funciona");
                return ResponseEntity.status(200).body(responseTest);
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
}
