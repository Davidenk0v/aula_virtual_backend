package com.grupo2.aulavirtual.Controllers;

import java.util.List;

import com.grupo2.aulavirtual.Payload.Request.LessonDTO;
import com.grupo2.aulavirtual.Payload.Request.LessonsDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping("/")
    public ResponseEntity<?> getAllLessonDTO() {

        return null;
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getLessonById(@PathVariable Long id) {

        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> saveLesson(@RequestBody LessonsDTO Lesson) {

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLesson(@RequestBody LessonsDTO Lesson, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long id) {

        return null;
    }
}
