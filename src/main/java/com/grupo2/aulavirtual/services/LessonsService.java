package com.grupo2.aulavirtual.services;

import com.grupo2.aulavirtual.payload.request.LessonsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface LessonsService {
    ResponseEntity<?> lessonsList();

    ResponseEntity<HashMap<String, ?>> postLessons(Long idSubject, LessonsDTO lessonsDTO, MultipartFile file);

    ResponseEntity<HashMap<String, ?>> deleteLesson(Long id);

    ResponseEntity<HashMap<String, ?>> updateLesson(Long id, LessonsDTO lessonsDTO, MultipartFile file);

    ResponseEntity<HashMap<String, ?>> findLessonsById(Long id);
}
