package com.grupo2.aulavirtual.Controllers;

import java.util.List;

import com.grupo2.aulavirtual.Entities.Lesson.Entity;
import com.grupo2.aulavirtual.Payload.Request.LessonsDTO;


@RestController
@RequestMapping("/")
public class CourseController {

    @Autowired
    private LessonService lessonService;

    @GetMapping
    public List<LessonDTO> getLessonsDTO() {

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long id) {

        return null;
    }

    @PostMapping
    public LessonEntity saveLesson(@RequestBody LessonEntity lesson) {

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLessonById(@PathVariable Long id) {

        return null;
    }

}