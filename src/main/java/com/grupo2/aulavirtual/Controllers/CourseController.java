package com.grupo2.aulavirtual.Controllers;

import com.grupo2.aulavirtual.Payload.Request.CourseDTO;
import com.grupo2.aulavirtual.Services.CoursesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {

    @Autowired
    private CoursesService courseService;

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