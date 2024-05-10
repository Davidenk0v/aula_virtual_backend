package com.grupo2.aulavirtual.controllers;

import com.grupo2.aulavirtual.entities.LessonsEntity;
import com.grupo2.aulavirtual.payload.request.LessonsDTO;

import com.grupo2.aulavirtual.services.impl.LessonsServiceImpl;
import com.grupo2.aulavirtual.util.FileUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/lessons")
@Tag(name = "Lessons API", description = "API REST para la gestión de lecciones")
public class LessonController {

    @Autowired
    private LessonsServiceImpl lessonService;

    @Operation(summary = "Obtener todas las lecciones", tags = "Lessons API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operación realizada con éxito", content = @Content),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<?> getAllLessonsDTO() {
        return lessonService.lessonsList();
    }

    @Operation(summary = "Obtener una lección por ID", tags = "Lessons API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operación realizada con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LessonsEntity.class))),
            @ApiResponse(responseCode = "404", description = "Leccion no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getLessonById(@PathVariable Long id) {
        return lessonService.findLessonsById(id);
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<?> uploadFile(@PathVariable Long id) {
        return lessonService.sendFile(id);
    }

    @Operation(summary = "Crear una nueva lección", tags = "Lessons API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Leccion creada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LessonsEntity.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content)
    })
    @PostMapping("/{idSubject}")
    public ResponseEntity<?> saveLesson(@PathVariable Long idSubject, @RequestBody LessonsDTO lesson, @RequestParam("file") MultipartFile file) {
        return lessonService.postLessons(idSubject, lesson, file);
    }

    @PostMapping("/file/{id}")
    public ResponseEntity<?> saveFile(@PathVariable Long id,@RequestParam("file") MultipartFile file) {
        return lessonService.saveFile(id, file);
    }

    @Operation(summary = "Actualizar una lección por ID", tags = "Lessons API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Leccion actualizada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LessonsEntity.class))),
            @ApiResponse(responseCode = "404", description = "Leccion no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLesson(@RequestBody LessonsDTO lessonsDTO, @PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return lessonService.updateLesson(id, lessonsDTO, file);
    }

    @Operation(summary = "Eliminar una lección por ID", tags = "Lessons API")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Leccion eliminada"),
            @ApiResponse(responseCode = "404", description = "Leccion no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLessonById(@PathVariable Long id) {
        return lessonService.deleteLesson(id);
    }
}
