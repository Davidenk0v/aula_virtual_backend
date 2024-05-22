package com.grupo2.aulavirtual.services;

import com.grupo2.aulavirtual.entities.UserImg;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    ResponseEntity<?> downloadFile(String id, MultipartFile file);

    ResponseEntity<?> saveFile(UserImg user, MultipartFile file);

    ResponseEntity<?> updateFile(UserImg user, MultipartFile file);

    ResponseEntity<?> sendFile(String id);

    ResponseEntity<?> setDefaultImage(String id);
}
