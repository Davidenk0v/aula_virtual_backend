package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.entities.CommentEntity;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.UserImg;
import com.grupo2.aulavirtual.repositories.ImageRepository;
import com.grupo2.aulavirtual.services.KeycloakService;
import com.grupo2.aulavirtual.util.mappers.DtoMapper;
import com.grupo2.aulavirtual.payload.request.CommentDTO;
import com.grupo2.aulavirtual.payload.response.CommentResponseDto;
import com.grupo2.aulavirtual.repositories.CommentRepository;
import com.grupo2.aulavirtual.repositories.CourseRepository;
import com.grupo2.aulavirtual.services.CommentService;
import com.grupo2.aulavirtual.util.files.FileUtil;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Clase de servicios que implementa los metodos de la interfaz CommentService.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private KeycloakService keycloakService;

    @Autowired
    private CourseRepository courseRepository;

    DtoMapper dtoMapper = new DtoMapper();
    private static final String SAVE = "data";
    private static final String ERROR = "error";
    FileUtil fileUtil = new FileUtil();

    @Value("${fileutil.default.img.user}")
    private String defaultImg;
    @Value("${fileutil.user.folder.path}")
    private String userFolder;

    /**
     * Metodo para crear un comentario.
     * 
     * @param idUser     Long con la id del usuario.
     * @param idCourse   Long con la id del course.
     * @param commentDTO DTO con los datos del comentario.
     * @return ResponseEntity con el estado de la operacion.
     */
    @Override
    public ResponseEntity<?> postComment(String idUser, Long idCourse, CommentDTO commentDTO) {
        try {
            HashMap<String, CommentResponseDto> response = new HashMap<>();
            CourseEntity course = courseRepository.findById(idCourse).get();

            CommentEntity comment = new CommentEntity();
            comment = dtoMapper.dtoToEntity(commentDTO);

            comment.setCourse(course);
            comment.setUserId(idUser);
            CommentResponseDto objectResponse = dtoMapper.entityToResponseDto(comment);

            commentRepository.save(comment);

            return ResponseEntity.status(201).body(objectResponse);

        } catch (Exception e) {
            HashMap<String, Object> comentarios = new HashMap<>();
            comentarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(comentarios);
        }
    }

    /**
     * Metodo para regresar una lista de comentarios.
     * 
     * @return ResponseEntity<> con una List<CommentResponseDto> si hay datos,
     *         sino un string.
     */
    @Override
    public ResponseEntity<?> commentList() {
        List<CommentEntity> commentEntities = commentRepository.findAll();
        if (commentEntities.isEmpty()) {
            return new ResponseEntity<>(ERROR, HttpStatus.NOT_FOUND);
        }
        List<CommentResponseDto> commentResponseDtos = commentEntities.stream()
                .map(commentEntity -> dtoMapper.entityToResponseDto(commentEntity)).toList();
        return new ResponseEntity<>(commentResponseDtos, HttpStatus.OK);
    }

    /**
     * Metodo para buscar un comentario por su id.
     * 
     * @param id int con la id del comentario.
     * @return ResponseEntity<> con un CommentResponseDto si hay datos,
     *         sino un string.
     */
    @Override
    public ResponseEntity<?> findCommentById(int id) {
        try {
            HashMap<String, CommentResponseDto> response = new HashMap<>();
            if (commentRepository.existsById(id)) {
                CommentEntity comment = commentRepository.findById(id).get();
                return ResponseEntity.status(200).body(dtoMapper.entityToResponseDto(comment));
            } else {
                HashMap<String, Integer> error = new HashMap<>();
                error.put(ERROR, id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> comentarios = new HashMap<>();
            comentarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(comentarios);
        }
    }

    /**
     * Metodo para actualizar un comentario.
     * 
     * @param id         int con la id del comentario a actualizar.
     * @param commentDTO DTO con la informacion a cambiar.
     * @return
     */
    @Override
    public ResponseEntity<?> updateComment(int id, CommentDTO commentDTO) {
        try {
            HashMap<String, CommentResponseDto> response = new HashMap<>();
            if (commentRepository.existsById(id)) {
                CommentEntity comment = commentRepository.findById(id).get();
                if (commentDTO.getText() != "") {
                    comment.setText(commentDTO.getText());
                }
                if (commentDTO.getDate().toString() != "") {
                    comment.setDate(commentDTO.getDate());
                }
                if (commentDTO.getUserId() != null) {
                    comment.setUserId(commentDTO.getUserId());
                }
                if (commentDTO.getCourse() != null) {
                    comment.setCourse(commentDTO.getCourse());
                }
                commentRepository.save(comment);
                response.put(SAVE, dtoMapper.entityToResponseDto(comment));
                return ResponseEntity.status(200).body(response);
            } else {
                HashMap<String, Integer> error = new HashMap<>();
                error.put(ERROR, id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> comentarios = new HashMap<>();
            comentarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(comentarios);
        }
    }

    @Override
    public ResponseEntity<?> findCommentsByIdCourse(long idCourse) {

        try {
            HashMap<String, String> response = new HashMap<>();
            List<CommentEntity> commentsByCourse = commentRepository.findCommentsByIdCourse(idCourse);
            if (commentsByCourse != null) {
                List<CommentResponseDto> commentResponseDtos = commentsByCourse.stream()
                        .map(commentEntity -> dtoMapper.entityToResponseDto(commentEntity)).toList();
                return ResponseEntity.status(200).body(commentResponseDtos);
            } else {
                HashMap<String, Long> error = new HashMap<>();
                error.put(ERROR, idCourse);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> comentarios = new HashMap<>();
            comentarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(comentarios);
        }
    }

    /**
     * Metodo para eliminar un comentario por su id.
     * 
     * @param id int con la ide del comentario a eliminar.
     * @return ResponseEntity con el estado de la operacion.
     */
    @Override
    public ResponseEntity<?> deleteComment(int id) {
        try {
            HashMap<String, String> response = new HashMap<>();
            if (commentRepository.existsById(id)) {
                CommentEntity comment = commentRepository.findById(id).get();
                commentRepository.delete(comment);
                response.put(SAVE, "Se ha eliminado correctamente");
                return ResponseEntity.status(200).body(response);
            } else {
                HashMap<String, Integer> error = new HashMap<>();
                error.put(ERROR, id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> comentarios = new HashMap<>();
            comentarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(comentarios);
        }
    }

    /**
     * Metodo para enviar un archivo al frontend.
     * 
     * @param id Long con la id del usuario.
     * @return ResponseEntity<?> con la imagen, con string en caso de error.
     */
    public ResponseEntity<?> sendFile(String id) {
        UserRepresentation userRepresentation = keycloakService.findUserById(id);
        if (userRepresentation != null) {
            Optional<UserImg> userImg = imageRepository.findByIdUser(id);
            if (userImg.isPresent()) {
                UserImg user = userImg.get();
                String fileRoute = getCustomPath(user.getIdUser()) + user.getUrlImg();
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

    private String getCustomPath(String courseNane) {
        return userFolder + courseNane + "\\Image\\";
    }

}