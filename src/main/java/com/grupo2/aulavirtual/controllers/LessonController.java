package com.grupo2.aulavirtual.controllers;

import java.util.HashMap;

import com.grupo2.aulavirtual.payload.request.LessonsDTO;

import com.grupo2.aulavirtual.services.impl.LessonsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Crear una nueva lección", tags = "Lessons API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Leccion creada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LessonsEntity.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content)
    })
    @PostMapping("/{idSubject}")
    public ResponseEntity<?> saveLesson(@PathVariable Long idSubject, @RequestBody LessonsDTO lesson) {
        return lessonService.postLessons(idSubject, lesson);
    }

    @Operation(summary = "Actualizar una lección por ID", tags = "Lessons API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Leccion actualizada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LessonsEntity.class))),
            @ApiResponse(responseCode = "404", description = "Leccion no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLesson(@RequestBody LessonsDTO lessonsDTO, @PathVariable Long id) {
        return lessonService.updateLesson(id, lessonsDTO);
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
