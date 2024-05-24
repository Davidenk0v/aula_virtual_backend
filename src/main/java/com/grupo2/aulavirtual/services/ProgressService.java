package com.grupo2.aulavirtual.services;

import org.springframework.http.ResponseEntity;

public interface ProgressService {
    ResponseEntity<?> postProgress(String idKeyCloak, Long idCourse);

    ResponseEntity<?> sumValue(String idKeyCloak, Long idCourse, float progress);

    ResponseEntity<?> getProgress(String idKeyCloak, Long idCourse);

    ResponseEntity<?> deleteProgress(String idKeyCloak, Long idCourse);
}
