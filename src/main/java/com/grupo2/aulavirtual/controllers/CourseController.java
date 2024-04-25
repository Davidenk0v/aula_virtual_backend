package com.grupo2.aulavirtual.controllers;

import com.grupo2.aulavirtual.payload.request.CourseDTO;
import com.grupo2.aulavirtual.services.impl.CoursesServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/courses")
public class CourseController {

    @Autowired
    private CoursesServiceImpl courseService;

    @GetMapping("/")
    public ResponseEntity<?> getAllCoursesDTO() {
        return courseService.courseList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        return courseService.findCourseById(id);
    }

    @PostMapping("/{idTeacher}")
    public ResponseEntity<?> saveCourse(@RequestBody CourseDTO courseDTO, @PathVariable Long idTeacher) {
        return courseService.postCourse(idTeacher, courseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@RequestBody CourseDTO course, @PathVariable Long id) {
        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }

}