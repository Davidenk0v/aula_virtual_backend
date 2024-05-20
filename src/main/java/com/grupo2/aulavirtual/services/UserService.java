package com.grupo2.aulavirtual.services;

import com.grupo2.aulavirtual.entities.UserEntity;
import com.grupo2.aulavirtual.payload.request.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface UserService {
    UserEntity getLoggedUser();

    void syncUser(UserEntity user);

    ResponseEntity<HashMap<String, Object>> addUser(UserDTO userDTO);

    ResponseEntity<?> downloadFile(Long id, MultipartFile file);

    ResponseEntity<?> saveFile(UserEntity user, MultipartFile file);

    ResponseEntity<?> updateFile(UserEntity user, MultipartFile file);

    ResponseEntity<?> findUserByEmail(String email);

    ResponseEntity<?> findUserByUsername(String username);

    ResponseEntity<?> findUserById(String idUser);

    ResponseEntity<HashMap<String, ?>> updateUser(UserDTO userDTO, String idUser, MultipartFile file);

    ResponseEntity<?> sendFile(Long id);


    ResponseEntity<?> userCoursesList(String idUser);

    ResponseEntity<?> userList();

    ResponseEntity<HashMap<String, ?>> deleteUser(Long id);

    ResponseEntity<?> setDefaultImage(Long id);
}
