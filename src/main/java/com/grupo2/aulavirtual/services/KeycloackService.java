package com.grupo2.aulavirtual.services;

import com.grupo2.aulavirtual.payload.keycloack.UserDtoKeycloack;
import com.grupo2.aulavirtual.payload.request.UserDTO;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeycloackService {

    List<UserRepresentation> findAllUsers();
    List<UserRepresentation> searchUserByUsername(String username);
    String createUser(UserDtoKeycloack userDTO);
    void deleteUser(String userId);
    void updateUser(String userId, UserDtoKeycloack userDTO);
}
