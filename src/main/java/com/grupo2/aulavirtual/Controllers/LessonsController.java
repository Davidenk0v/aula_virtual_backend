package com.grupo2.aulavirtual.Controllers;

import java.util.List;

import com.grupo2.aulavirtual.Entities.LessonsEntity;
import com.grupo2.aulavirtual.Payload.Request.LessonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping("/{id}")
    public List<LessonDTO> getLessonDTO() {

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long id) {

        return null;
    }

    @PostMapping("/{id}")
    public LessonEntity saveLesson(@RequestBody LessonEntity lesson) {

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLessonById(@PathVariable Long id) {

        return null;
    }

}