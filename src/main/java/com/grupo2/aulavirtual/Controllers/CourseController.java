package com.grupo2.aulavirtual.Controllers;

import com.grupo2.aulavirtual.Entities.CourseEntity;
import com.grupo2.aulavirtual.Payload.Request.CourseDTO;
import com.grupo2.aulavirtual.Services.CoursesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/courses")
@Tag(name = "Courses API", description = "API REST para la gestión de cursos")
public class CourseController {

    @Autowired
    private CoursesService courseService;

    @Operation(summary = "Obtener todos los cursos", tags = "Courses API")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operación realizada con éxito", content = @Content),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<?> getAllCoursesDTO() {
        return courseService.courseList();
    }

    @Operation(summary = "Obtener un curso por ID", tags = "Courses API")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operación realizada con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseEntity.class))),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        return courseService.findCourseById(id);
    }

    @Operation(summary = "Crear un nuevo curso", tags = "Courses API")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Curso creado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseEntity.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content)
    })
    @PostMapping("/{idTeacher}")
    public ResponseEntity<?> saveCourse(@RequestBody CourseDTO courseDTO, @PathVariable Long idTeacher) {
        return courseService.postCourse(idTeacher, courseDTO);
    }

    @Operation(summary = "Actualizar un curso por ID", tags = "Courses API")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Curso actualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseEntity.class))),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@RequestBody CourseDTO course, @PathVariable Long id) {
        return courseService.updateCourse(id, course);
    }

    @Operation(summary = "Eliminar un curso por ID", tags = "Courses API")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Curso eliminado"),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }

}
