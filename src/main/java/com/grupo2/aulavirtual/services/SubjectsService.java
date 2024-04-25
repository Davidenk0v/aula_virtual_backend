package com.grupo2.aulavirtual.services;

import com.grupo2.aulavirtual.payload.request.SubjectDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface SubjectsService {
    ResponseEntity<?> subjectsList();

    ResponseEntity<HashMap<String, ?>> postSubject(Long idCourse, SubjectDTO subjectDTO);

    ResponseEntity<HashMap<String, ?>> deleteSubject(Long id);

    ResponseEntity<HashMap<String, ?>> updateSubject(Long id, SubjectDTO subjectDTO);

    ResponseEntity<HashMap<String, ?>> findSubjectById(Long id);
}
