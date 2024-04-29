package com.grupo2.aulavirtual.Controllers;

import com.grupo2.aulavirtual.Payload.Request.SubjectDTO;
import com.grupo2.aulavirtual.Services.SubjectsService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/subjects")
@Tag(name = "Subjects API", description = "API REST para la gestión de asignaturas")
public class SubjectController {

    @Autowired
    private SubjectsService subjectService;

    @Operation(summary = "Obtener todas las asignaturas", tags = "Subjects API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación realizada con éxito", content = @Content),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<?> getAllSubjectsDTO() {
        return subjectService.subjectsList();
    }

    @Operation(summary = "Obtener una asignatura por ID", tags = "Subjects API")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operación realizada con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubjectDTO.class))),
        @ApiResponse(responseCode = "404", description = "Asignatura no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable Long id) {
        return subjectService.findSubjectById(id);
    }

    @Operation(summary = "Crear una nueva asignatura", tags = "Subjects API")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Asignatura creada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubjectDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content)
    })
    @PostMapping("/{idCourse}")
    public ResponseEntity<?> saveSubject(@PathVariable Long idCourse,@RequestBody SubjectDTO subject) {
        return subjectService.postSubject(idCourse, subject);
    }

    @Operation(summary = "Actualizar una asignatura por ID", tags = "Subjects API")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Asignatura actualizada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubjectDTO.class))),
        @ApiResponse(responseCode = "404", description = "Asignatura no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubject(@RequestBody SubjectDTO subject, @PathVariable Long id) {
        return subjectService.updateSubject(id,subject);
    }

    @Operation(summary = "Eliminar una asignatura por ID", tags = "Subjects API")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Asignatura eliminada"),
        @ApiResponse(responseCode = "404", description = "Asignatura no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubjectById(@PathVariable Long id) {
        return subjectService.deleteSubject(id);
    }
}

