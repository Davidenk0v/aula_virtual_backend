package com.grupo2.aulavirtual.services;

import com.grupo2.aulavirtual.payload.request.LessonsDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface LessonsService {
    ResponseEntity<?> lessonsList();

    ResponseEntity<HashMap<String, ?>> postLessons(Long idSubject, LessonsDTO lessonsDTO);

    ResponseEntity<HashMap<String, ?>> deleteLesson(Long id);

    ResponseEntity<HashMap<String, ?>> updateLesson(Long id, LessonsDTO lessonsDTO);

    ResponseEntity<HashMap<String, ?>> findLessonsById(Long id);
}
