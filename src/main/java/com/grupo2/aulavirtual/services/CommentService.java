package com.grupo2.aulavirtual.services;

import com.grupo2.aulavirtual.payload.request.CommentDTO;
import com.grupo2.aulavirtual.payload.request.CourseDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

/**
 * Interfaz del servicio de Comentarios.
 */
public interface CommentService {

    /**
     * Crea un comentario.
     * @param commentDTO DTO con los datos del comentario.
     * @return ResponseEntity
     */
    ResponseEntity<?> postComment(Long idUser, Long idCourse, CommentDTO commentDTO);

    /**
     * Regresa una lista con todos los comentarios.
     * @return ResponseEntity
     */
    ResponseEntity<?> commentList();

    /**
     * Regresa un comentario por su id.
     * @param id int con la id del comentario.
     * @return ResponseEntity
     */
    ResponseEntity<?> findCommentById(int id);

    /**
     * Actualiza un comentario.
     * @param id int con la id del comentario a actualizar.
     * @param commentDTO DTO con la informacion a cambiar.
     * @return ResponseEntity
     */
    ResponseEntity<?> updateComment(int id, CommentDTO commentDTO);

    /**
     * Elimina un comentario por su id.
     * @param id int con la ide del comentario a eliminar.
     * @return ResponseEntity
     */
    ResponseEntity<?> deleteComment(int id);

    ResponseEntity<?> findCommentsByIdCourse(long idCourse);

}
