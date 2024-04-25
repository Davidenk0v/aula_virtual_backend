package com.grupo2.aulavirtual.Controllers;

import java.util.HashMap;
import java.util.List;

import com.grupo2.aulavirtual.Payload.Request.LessonsDTO;

import com.grupo2.aulavirtual.Services.LessonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lesson")
public class LessonController {

    @Autowired
    private LessonsService lessonService;

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
    public ResponseEntity<HashMap<String,?>> deleteAddressById(@PathVariable Long id) {
        return lessonService.deleteLesson(id);
    }
}
