package com.grupo2.aulavirtual.services;

import com.grupo2.aulavirtual.payload.request.CourseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface CourseService {
    ResponseEntity<?> courseList();

    ResponseEntity<?> pageableCourseList(@NonNull Pageable pageable);

    ResponseEntity<HashMap<String, ?>> postCourse(Long idUser, CourseDTO courseDTO, MultipartFile file);

    ResponseEntity<?> deleteCourse(Long id);

    ResponseEntity<HashMap<String, ?>> updateCourse(Long id, CourseDTO courseDTO, MultipartFile file);

    ResponseEntity<?> findCourseById(Long id);

    ResponseEntity<HashMap<String, ?>> findAllByContains(String name);
}
