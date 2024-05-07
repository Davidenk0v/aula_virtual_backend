package com.grupo2.aulavirtual.services;

import com.grupo2.aulavirtual.payload.request.CourseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import java.util.HashMap;

public interface CourseService {
    ResponseEntity<?> courseList();

    ResponseEntity<?> pageableCourseList(@NonNull Pageable pageable);

    ResponseEntity<HashMap<String, ?>> postCourse(Long idUser, CourseDTO courseDTO);

    ResponseEntity<?> deleteCourse(Long id);

    ResponseEntity<HashMap<String, ?>> updateCourse(Long id, CourseDTO courseDTO);

    ResponseEntity<HashMap<String, ?>> findCourseById(Long id);

    ResponseEntity<HashMap<String, ?>> findAllByContains(String name);
}
