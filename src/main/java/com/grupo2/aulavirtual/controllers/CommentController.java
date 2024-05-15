package com.grupo2.aulavirtual.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo2.aulavirtual.payload.request.CommentDTO;
import com.grupo2.aulavirtual.services.impl.CommentServiceImpl;

import java.awt.print.Pageable;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @GetMapping("/")
    public ResponseEntity<?> getAllComments(){
        return commentService.commentList();
    }
    
/* 
    @GetMapping("/pages")
    public ResponseEntity<?> getAllPageable(Pageable pageable){
        return commentService.pageableCommentList(pageable);
    }
*/

    @GetMapping("/{idCourse}/course") 
    public ResponseEntity<?> getCommentsByIdCourse(@PathVariable long idCourse){
        return commentService.findCommentsByIdCourse(idCourse); 
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?>getCommentById(
            @PathVariable int  id){
        return commentService.findCommentById(id);
    }
    @PostMapping("/{idUser}/{idCourse}")
    public ResponseEntity<?>saveComment(
            @PathVariable String idUser,
            @PathVariable Long idCourse,
            @RequestBody CommentDTO commentDTO){
        return commentService.postComment(idUser, idCourse,commentDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>updateComment(
            @PathVariable int id,
            @RequestBody CommentDTO commentDTO){
        return commentService.updateComment(id,commentDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteComment(
            @PathVariable int id){
        return commentService.deleteComment(id);
    }
}
