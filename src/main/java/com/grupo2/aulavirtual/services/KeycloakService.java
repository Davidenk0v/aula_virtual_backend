package com.grupo2.aulavirtual.services;

import com.grupo2.aulavirtual.payload.request.LoginRequestDto;
import com.grupo2.aulavirtual.payload.request.RegisterRequestDto;
import com.grupo2.aulavirtual.payload.request.UserDTO;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

public interface KeycloakService {
    ResponseEntity<?> findAllUsers();

    ResponseEntity<?> loginUser(LoginRequestDto loginRequest);

    ResponseEntity<?> logoutUser(String idUser);

    ResponseEntity<?> searchUserByUsername(String username);

    ResponseEntity<?> searchUserById(String userId);

    UserRepresentation findUserById(String userId);

    UserRepresentation findUserByEmail(String email);

    ResponseEntity<?> createUser(@NonNull RegisterRequestDto userDTO);

    ResponseEntity<?> deleteUser(String userId);

    ResponseEntity<?> updateUser(String userId, @NonNull UserDTO userDTO);
}
