package com.grupo2.aulavirtual.tests.controller;
import com.grupo2.aulavirtual.controllers.CommentController;
import com.grupo2.aulavirtual.payload.request.CommentDTO;
import com.grupo2.aulavirtual.services.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentServiceImpl commentService;

    @Test
    public void testGetAllComments() throws Exception {
        when(commentService.commentList()).thenReturn(ResponseEntity.ok(any()));

        mockMvc.perform(get("/api/v1/comment/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetCommentsByIdCourse() throws Exception {
        long courseId = 1L;
        when(commentService.findCommentsByIdCourse(courseId)).thenReturn(ResponseEntity.ok(any()));

        mockMvc.perform(get("/api/v1/comment/{idCourse}/course", courseId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetCommentById() throws Exception {
        int commentId = 1;
        ResponseEntity<?> responseEntity = ResponseEntity.ok().body(new CommentDTO());
        when(commentService.findCommentById(commentId)).thenReturn(any());

        mockMvc.perform(get("/api/v1/comment/{id}", commentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testSaveComment() throws Exception {
        String userId = "user1";
        long courseId = 1L;
        CommentDTO commentDTO = new CommentDTO();

        when(commentService.postComment(eq(userId), eq(courseId), any(CommentDTO.class))).thenReturn(any());

        mockMvc.perform(post("/api/v1/comment/{idUser}/{idCourse}", userId, courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateComment() throws Exception {
        int commentId = 1;
        CommentDTO commentDTO = new CommentDTO();

        when(commentService.updateComment(eq(commentId), any(CommentDTO.class))).thenReturn(any());

        mockMvc.perform(put("/api/v1/comment/{id}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testDeleteComment() throws Exception {
        int commentId = 1;
        when(commentService.deleteComment(commentId)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/api/v1/comment/{id}", commentId))
                .andExpect(status().isOk());
    }

    @Test
    public void testUploadFile() throws Exception {
        String userId = "user1";
        when(commentService.sendFile(userId)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/api/v1/comment/file/{id}", userId))
                .andExpect(status().isOk());
    }
}
