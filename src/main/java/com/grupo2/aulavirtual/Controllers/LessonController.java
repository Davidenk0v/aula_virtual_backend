package com.grupo2.aulavirtual.Controllers;

import java.util.HashMap;
import java.util.List;

import com.grupo2.aulavirtual.Entities.LessonsEntity;
import com.grupo2.aulavirtual.Payload.Request.LessonsDTO;
import com.grupo2.aulavirtual.Services.LessonsService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/lessons")
@Tag(name = "Lessons API", description = "API REST para la gestión de lecciones")
public class LessonController {

    @Autowired
    private LessonsService lessonService;

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
    public ResponseEntity<?> saveLesson(@PathVariable Long idSubject,@RequestBody LessonsDTO lesson) {
        return lessonService.postLessons(idSubject,lesson);
    }

    @Operation(summary = "Actualizar una lección por ID", tags = "Lessons API")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Leccion actualizada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LessonsEntity.class))),
        @ApiResponse(responseCode = "404", description = "Leccion no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLesson(@RequestBody LessonsDTO lessonsDTO, @PathVariable Long id) {
        return lessonService.updateLesson(id,lessonsDTO);
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

