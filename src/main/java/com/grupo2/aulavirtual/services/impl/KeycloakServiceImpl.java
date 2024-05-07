package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.entities.UserEntity;
import com.grupo2.aulavirtual.entities.enums.RoleEnum;
import com.grupo2.aulavirtual.mappers.DtoMapper;
import com.grupo2.aulavirtual.payload.request.LoginRequestDto;
import com.grupo2.aulavirtual.payload.request.RegisterRequestDto;
import com.grupo2.aulavirtual.payload.request.UserDTO;
import com.grupo2.aulavirtual.repositories.UserRepository;
import com.grupo2.aulavirtual.services.KeycloakService;
import com.grupo2.aulavirtual.util.KeycloakProvider;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class KeycloakServiceImpl implements KeycloakService {

    Logger logger = LoggerFactory.getLogger(KeycloakServiceImpl.class);

    @Autowired
    private DtoMapper mapper;

    @Autowired
    private UserRepository userRepository;
    /**
     * Metodo para listar todos los usuarios de Keycloak
     *
     * @return List<UserRepresentation>
     */
    @Override
    public List<UserRepresentation> findAllUsers() {
        return KeycloakProvider.getRealmResource()
                .users()
                .list();
    }

    @Override
    public ResponseEntity<?> loginUser(LoginRequestDto loginRequest) {
        try (Keycloak keycloak = KeycloakProvider.newKeycloakBuilderWithPasswordCredentials(loginRequest.username(),
                loginRequest.password())) {

            AccessTokenResponse token = keycloak.tokenManager().getAccessToken();
            return ResponseEntity.status(200).body(token);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials. Please try again.");
        }
    }


    /**
     * Metodo para buscar un usuario por su username
     *
     * @return List<UserRepresentation>
     */
    @Override
    public List<UserRepresentation> searchUserByUsername(String username) {
        return KeycloakProvider.getRealmResource()
                .users()
                .searchByUsername(username, true);
    }


    /**
     * Metodo para crear un usuario en keycloak
     *
     * @return String
     */
    @Override
    public ResponseEntity<String> createUser(@NonNull RegisterRequestDto userDTO) {

        int status = 0;
        UsersResource usersResource = KeycloakProvider.getUserResource();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(userDTO.getFirstname());
        userRepresentation.setLastName(userDTO.getLastname());
        userRepresentation.setEmail(userDTO.getEmail());
        userRepresentation.setUsername(userDTO.getUsername());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);

        Response response = usersResource.create(userRepresentation);

        status = response.getStatus();

        if (status == 201) {
            String path = response.getLocation().getPath();
            String userId = path.substring(path.lastIndexOf("/") + 1);

            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(userDTO.getPassword());

            usersResource.get(userId).resetPassword(credentialRepresentation);

            RealmResource realmResource = KeycloakProvider.getRealmResource();

            List<RoleRepresentation> rolesRepresentation = null;

            if (userDTO.getRole() == null || userDTO.getRole().isEmpty()) {
                rolesRepresentation = List.of(realmResource.roles().get("student_class_room").toRepresentation());
            } else {
                rolesRepresentation = realmResource.roles()
                        .list()
                        .stream()
                        .filter(role -> userDTO.getRole()
                                .stream()
                                .anyMatch(roleName -> roleName.equalsIgnoreCase(role.getName())))
                        .toList();
            }

            realmResource.users().get(userId).roles().realmLevel().add(rolesRepresentation);

            UserEntity userEntity = new UserEntity();

                    userEntity.builder()
                    .email(userDTO.getEmail())
                    .firstname(userDTO.getFirstname())
                    .lastname(userDTO.getLastname())
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .build();

            userRepository.save(userEntity);


            return ResponseEntity.status(201).body("User created successfully");

        } else if (status == 409) {
            log.error("User exist already!");
            return ResponseEntity.status(409).body("User exist already!");
        } else {
            log.error("Error creating user, please contact with the administrator.");
            return ResponseEntity.status(500).body("Error creating user, please contact with the administrator.");
        }
    }


    /**
     * Metodo para borrar un usuario en keycloak
     *
     * @return void
     */
    @Override
    public void deleteUser(String userId) {
        try {
            KeycloakProvider.getUserResource()
                    .get(userId)
                    .remove();
        } catch (Exception e) {
            logger.error("Error deleting user: " + e.getMessage());
        }
    }


    /**
     * Metodo para actualizar un usuario en keycloak
     *
     * @return void
     */
    @Override
    public void updateUser(String userId, @NonNull UserDTO userDTO) {
        try {
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType(OAuth2Constants.PASSWORD);
            credentialRepresentation.setValue(userDTO.getPassword());

            UserRepresentation user = new UserRepresentation();
            user.setUsername(userDTO.getUsername());
            user.setFirstName(userDTO.getFirstname());
            user.setLastName(userDTO.getLastname());
            user.setEmail(userDTO.getEmail());
            user.setEnabled(true);
            user.setEmailVerified(true);
            user.setCredentials(Collections.singletonList(credentialRepresentation));

            UserResource usersResource = KeycloakProvider.getUserResource().get(userId);
            usersResource.update(user);
        } catch (Exception e) {
            logger.error("Error updating user: " + e.getMessage());
        }
    }
}