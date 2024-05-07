package com.grupo2.aulavirtual.services;

import com.grupo2.aulavirtual.payload.request.LoginRequestDto;
import com.grupo2.aulavirtual.payload.request.RegisterRequestDto;
import com.grupo2.aulavirtual.payload.request.UserDTO;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import java.util.List;

public interface KeycloakService {
    List<UserRepresentation> findAllUsers();

    ResponseEntity<?> loginUser(LoginRequestDto loginRequest);

    List<UserRepresentation> searchUserByUsername(String username);

    ResponseEntity<String> createUser(@NonNull RegisterRequestDto userDTO);

    void deleteUser(String userId);

    void updateUser(String userId, @NonNull UserDTO userDTO);
}
