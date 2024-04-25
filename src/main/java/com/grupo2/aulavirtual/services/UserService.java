package com.grupo2.aulavirtual.services;

import com.grupo2.aulavirtual.payload.request.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface UserService {
    ResponseEntity<HashMap<String, Object>> addUser(UserDTO userDTO);

    ResponseEntity<HashMap<String, Object>> findUserByEmail(String email);

    ResponseEntity<HashMap<String, Object>> findUserById(Long idUser);

    ResponseEntity<HashMap<String, ?>> updateUser(UserDTO userDTO, Long id);

    ResponseEntity<?> userList();

    ResponseEntity<HashMap<String, ?>> deleteUser(Long id);
}
