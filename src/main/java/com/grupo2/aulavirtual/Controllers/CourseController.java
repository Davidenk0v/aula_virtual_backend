package com.grupo2.aulavirtual.Controllers;

import java.util.List;

import com.grupo2.aulavirtual.Entities.CourseEntity;
import com.grupo2.aulavirtual.Payload.Request.CourseDTO;


@RestController
@RequestMapping("/")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<CourseDTO> getCoursesDTO() {

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {

        return null;
    }

    @PostMapping
    public CourseEntity saveCourse(@RequestBody CourseEntity course) {

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourseById(@PathVariable Long id) {

        return null;
    }

}