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
public class LessonController {

    @Autowired
    private LessonsServiceImpl lessonService;

    @GetMapping("/")
    public ResponseEntity<?> getAllLessonDTO() {
        return lessonService.lessonsList();
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getLessonById(@PathVariable Long id) {
        return lessonService.findLessonsById(id);
    }

    @PostMapping("/{idSubject}")
    public ResponseEntity<?> saveLesson(@PathVariable Long idSubject,@RequestBody LessonsDTO lesson) {
        return lessonService.postLessons(idSubject,lesson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLesson(@RequestBody LessonsDTO lessonsDTO, @PathVariable Long id) {
        return lessonService.updateLesson(id,lessonsDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String,?>> deleteLessonById(@PathVariable Long id) {
        return lessonService.deleteLesson(id);
    }
}
