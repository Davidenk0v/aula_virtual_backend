package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.entities.UserImg;
import com.grupo2.aulavirtual.payload.request.LoginRequestDto;
import com.grupo2.aulavirtual.payload.request.RegisterRequestDto;
import com.grupo2.aulavirtual.payload.request.UserDTO;
import com.grupo2.aulavirtual.repositories.ImageRepository;
import com.grupo2.aulavirtual.services.KeycloakService;
import com.grupo2.aulavirtual.util.files.FileUtil;
import com.grupo2.aulavirtual.util.keycloak.KeycloakProvider;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class KeycloakServiceImpl implements KeycloakService {

    Logger logger = LoggerFactory.getLogger(KeycloakServiceImpl.class);

    @Autowired
    private ImageRepository imageRepository;

    FileUtil fileUtil = new FileUtil();
    @Value("${fileutil.default.img.user}")
    private String defaultImg;

    private final String DATA = "Data";

    private final String ERROR = "Error";

    /**
     * Metodo para listar todos los usuarios de Keycloak
     *
     * @return List<UserRepresentation>
     */
    @Override
    public ResponseEntity<?> findAllUsers() {
        try {
            Map<String, List> response = new HashMap<>();
            List<UserRepresentation> userList = KeycloakProvider.getRealmResource()
                    .users()
                    .list();
            if (userList.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put(ERROR, "No se encontro ningun usuario");
                return ResponseEntity.status(404).body(error);
            }
            response.put(DATA, userList);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put(ERROR, "Error al buscar los usuarios");
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> loginUser(LoginRequestDto loginRequest) {
        try (Keycloak keycloak = KeycloakProvider.newKeycloakBuilderWithPasswordCredentials(loginRequest.username(),
                loginRequest.password())) {

            AccessTokenResponse token = keycloak.tokenManager().getAccessToken();
            return ResponseEntity.status(200).body(token);

        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(401).body("Invalid credentials. Please try again.");
        }
    }

    @Override
    public ResponseEntity<?> logoutUser(String idUser) {
        Map<String, String> response = new HashMap<>();
        try {
            UserResource userResource = KeycloakProvider.getUserResource().get(idUser);
            userResource.logout();
            response.put(DATA, "Se ha cerrado la sesion correctamente");
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            response.put(ERROR, "Error al cerrar la sesion");
            return ResponseEntity.status(500).body(response);
        }

    }

    /**
     * Metodo para buscar un usuario por su username
     *
     * @return List<UserRepresentation>
     */
    @Override
    public ResponseEntity<?> searchUserByUsername(String username) {
        try {
            List<UserRepresentation> userRepresentationList = KeycloakProvider.getRealmResource().users()
                    .searchByUsername(username, true);
            return ResponseEntity.status(200).body(userRepresentationList);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("No se encontro ningun usuario con ese username");

        }
    }

    /**
     * Metodo para buscar un usuario por su username
     *
     * @return List<UserRepresentation>
     */
    @Override
    public ResponseEntity<?> searchUserById(String userId) {
        try {
            UserResource userResource = KeycloakProvider.getRealmResource().users().get(userId);
            if (userResource == null) {
                return ResponseEntity.status(404).body("No se encontró ningún usuario con ese ID");
            }
            UserRepresentation user = userResource.toRepresentation();
            return ResponseEntity.status(200).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ocurrió un error al buscar el usuario");
        }
    }

    /**
     * Metodo para crear un usuario en keycloak
     *
     * @return String
     */
    @Override
    public ResponseEntity<?> createUser(@NonNull RegisterRequestDto userDTO) {

        int status = 0;
        UsersResource usersResource = KeycloakProvider.getUserResource();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(userDTO.getFirstname());
        userRepresentation.setLastName(userDTO.getLastname());
        userRepresentation.setEmail(userDTO.getEmail());
        userRepresentation.setUsername(userDTO.getUsername());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);

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
            String defaultUrlImage = fileUtil.setDefaultImage(defaultImg);

            imageRepository.save(
                    new UserImg().builder()
                            .idUser(userId)
                            .urlImg(defaultUrlImage)
                            .build());

            logger.info("User created successfully");
            Map<String, String> userCreated = new HashMap<>();
            userCreated.put(DATA, "User created succesfully");
            return ResponseEntity.status(201).body(userCreated);

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
    public ResponseEntity<?> deleteUser(String userId) {
        Map<String, String> response = new HashMap<>();
        try {
            KeycloakProvider.getUserResource()
                    .get(userId)
                    .remove();
            response.put(DATA, "Usuario eliminado");
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.put(ERROR, "Error al eliminar usuario");
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Metodo para actualizar un usuario en keycloak
     *
     * @return void
     */
    @Override
    public ResponseEntity<?> updateUser(String userId, @NonNull UserDTO userDTO) { // FALTA ACTUALIZAR LA FOTO
        Map<String, String> response = new HashMap<>();
        try {
            UserRepresentation user = new UserRepresentation();
            if (!Objects.equals(userDTO.getUsername(), "")) {
                user.setUsername(userDTO.getUsername());
            }
            if (!Objects.equals(userDTO.getLastname(), "")) {
                user.setLastName(userDTO.getLastname());
            }
            if (!Objects.equals(userDTO.getFirstname(), "")) {
                user.setFirstName(userDTO.getFirstname());
            }
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
                credentialRepresentation.setTemporary(false);
                user.setEmailVerified(true);
                credentialRepresentation.setType(OAuth2Constants.PASSWORD);
                credentialRepresentation.setValue(userDTO.getPassword());
                user.setCredentials(Collections.singletonList(credentialRepresentation));
            }

            user.setEnabled(true);
            user.setEmailVerified(true);

            UserResource usersResource = KeycloakProvider.getUserResource().get(userId);
            usersResource.update(user);
            response.put(DATA, "Usuario actualizado correctamente");
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.put(ERROR, "Error al actualizar el usuario");
            return ResponseEntity.status(500).body(response);
        }
    }

}