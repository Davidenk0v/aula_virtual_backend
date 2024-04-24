package com.grupo2.aulavirtual.Controllers;

import java.util.List;

import com.grupo2.aulavirtual.Entities.CourseEntity;
import com.grupo2.aulavirtual.Payload.Request.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/{id}")
    public List<CourseDTO> getCoursesDTO() {

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {

        return null;
    }

    @PostMapping("/{id}")
    public CourseEntity saveCourse(@RequestBody CourseEntity course) {

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourseById(@PathVariable Long id) {

        return null;
    }

}