package com.grupo2.aulavirtual.controllers;

import com.grupo2.aulavirtual.payload.request.CourseDTO;
import com.grupo2.aulavirtual.services.impl.CoursesServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getCategoriesByCategory(@PathVariable String category) {
        return courseService.findCoursesByCategory(category);
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity<?> courseByUser(@PathVariable String idUser) {
        return courseService.findCoursesByUser(idUser);
    }

    @GetMapping("/pages")
    public ResponseEntity<?> getAllPageable(Pageable pageable) {
        return courseService.pageableCourseList(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        return courseService.findCourseById(id);
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<?> uploadFile(@PathVariable Long id) {
        return courseService.sendFile(id);
    }

    @PostMapping("/{emailTeacher}")
    public ResponseEntity<?> saveCourse(@RequestBody CourseDTO courseDTO, @PathVariable String emailTeacher) {
        return courseService.postCourse(emailTeacher, courseDTO);
    }

    @PostMapping("/file/{id}")
    public ResponseEntity<?> saveFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return courseService.downloadFile(id, file);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@RequestBody CourseDTO course, @PathVariable Long id,
            @PathVariable Long idTeacher) {
        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }

    @DeleteMapping("/file/{id}")
    public ResponseEntity<?> setDefaultImage(@PathVariable Long id) {
        return courseService.setDefaultImage(id);
    }

    @GetMapping("/lista/{name}")
    public ResponseEntity<?> getCoursesByName(@PathVariable String name) {
        return courseService.findAllByContains(name);
    }

}