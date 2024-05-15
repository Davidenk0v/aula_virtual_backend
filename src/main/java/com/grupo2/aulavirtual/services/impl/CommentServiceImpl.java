package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.entities.CommentEntity;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.UserEntity;
import com.grupo2.aulavirtual.mappers.DtoMapper;
import com.grupo2.aulavirtual.payload.request.CommentDTO;
import com.grupo2.aulavirtual.payload.response.CommentResponseDto;
import com.grupo2.aulavirtual.repositories.CommentRepository;
import com.grupo2.aulavirtual.repositories.CourseRepository;
import com.grupo2.aulavirtual.repositories.UserRepository;
import com.grupo2.aulavirtual.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase de servicios que implementa los metodos de la interfaz CommentService.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    DtoMapper dtoMapper = new DtoMapper();

    /**
     * Metodo para crear un comentario.
     * @param idUser Long con la id del usuario.
     * @param idCourse Long con la id del course.
     * @param commentDTO DTO con los datos del comentario.
     * @return ResponseEntity con el estado de la operacion.
     */
    @Override
    public ResponseEntity<?> postComment(Long idUser, Long idCourse, CommentDTO commentDTO) {
        try {
            HashMap<String, CommentResponseDto> response = new HashMap<>();
            UserEntity user = userRepository.findById(idUser).get();
            CourseEntity course = courseRepository.findById(idCourse).get();
 

            CommentEntity comment = new CommentEntity();
            comment = dtoMapper.dtoToEntity(commentDTO);
 
            /*if (user.getComments() == null) {
                ArrayList<CommentEntity> lista = new ArrayList<>();
                lista.add(comment);
                user.setComments(lista);
            } else {
                List<CommentEntity> listaExist = user.getComments();
                listaExist.add(comment);
                user.setComments(listaExist);
            }
            if (course.getComments() == null) {
                ArrayList<CommentEntity> lista = new ArrayList<>();
                lista.add(comment);
                course.setComments(lista);
            } else {
                List<CommentEntity> listaExist = course.getComments();
                listaExist.add(comment);
                course.setComments(listaExist);
            }*/
 


            comment.setCourse(course);
            comment.setUser(user);
            CommentResponseDto objectResponse = dtoMapper.entityToResponseDto(comment);
 
            /*courseRepository.save(course);
            userRepository.save(user);*/
         

            commentRepository.save(comment);

            return ResponseEntity.status(201).body(objectResponse);
 
        }  catch (Exception e) {
            HashMap<String, Object> comentarios = new HashMap<>();
            comentarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(comentarios);
        }
    }

    /**
     * Metodo para regresar una lista de comentarios.
     * @return ResponseEntity<> con una List<CommentResponseDto> si hay datos,
     *  sino un string.
     */
    @Override
    public ResponseEntity<?> commentList() {
        List<CommentEntity> commentEntities = commentRepository.findAll();
        if(commentEntities.isEmpty()){
            return new ResponseEntity<>("No se encontraron los comentarios", HttpStatus.NOT_FOUND);
        }
        List<CommentResponseDto> commentResponseDtos = commentEntities.stream().map(commentEntity -> dtoMapper.entityToResponseDto(commentEntity)).toList();
        return new ResponseEntity<>(commentResponseDtos, HttpStatus.OK);
    }

    /**
     * Metodo para buscar un comentario por su id.
     * @param id int con la id del comentario.
     * @return ResponseEntity<> con un CommentResponseDto si hay datos,
     *  sino un string.
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
                error.put("No ha encontrado el comentario con id: ", id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> comentarios = new HashMap<>();
            comentarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(comentarios);
        }
    }
 
    /**
     * Metodo para actualizar un comentario.
     * @param id int con la id del comentario a actualizar.
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
                if (commentDTO.getUser() != null) {
                    comment.setUser(commentDTO.getUser());
                }
                if (commentDTO.getCourse() != null) {
                    comment.setCourse(commentDTO.getCourse());
                }
                commentRepository.save(comment);
                response.put("Se ha modificado correctamente", dtoMapper.entityToResponseDto(comment));
                return ResponseEntity.status(200).body(response);
            } else {
                HashMap<String, Integer> error = new HashMap<>();
                error.put("No ha encontrado el comentario con id: ", id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> comentarios = new HashMap<>();
            comentarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(comentarios);
        }
    }

    @Override
    public ResponseEntity<?> findCommentsByIdCourse(long idCourse) {

        try {
            HashMap<String, String> response = new HashMap<>();
            List<CommentEntity> commentsByCourse = commentRepository.findCommentsByIdCourse(idCourse);
            if (commentsByCourse!=null) {
                List<CommentResponseDto> commentResponseDtos = commentsByCourse.stream().map(commentEntity -> dtoMapper.entityToResponseDto(commentEntity)).toList();
                return ResponseEntity.status(200).body(commentResponseDtos);
            } else {
                HashMap<String, Long> error = new HashMap<>();
                error.put("No ha encontrado los comentarios por la id de curso: ", idCourse);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> comentarios = new HashMap<>();
            comentarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(comentarios);
        } 
    }  

    /**
     * Metodo para eliminar un comentario por su id.
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
                response.put("Ok","Se ha eliminado correctamente");
                return ResponseEntity.status(200).body(response);
            } else {
                HashMap<String, Integer> error = new HashMap<>();
                error.put("No ha encontrado el comentario con id: ", id);
                return ResponseEntity.status(404).body(error);
            }
        } catch (Exception e) {
            HashMap<String, Object> comentarios = new HashMap<>();
            comentarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(comentarios);
        }
    }

   
}