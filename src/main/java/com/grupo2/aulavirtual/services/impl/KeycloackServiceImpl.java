package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.payload.keycloack.UserDtoKeycloack;
import com.grupo2.aulavirtual.services.KeycloackService;
import com.grupo2.aulavirtual.util.KeycloackProvider;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KeycloackServiceImpl implements KeycloackService {
    @Override //Metedo para listar todos los usuarios de Keycloack
    public List<UserRepresentation> findAllUsers() {
        return KeycloackProvider.getRealmResource()
                .users()
                .list();
    }

    @Override
    public List<UserRepresentation> searchUserByUsername(String username) {
        return KeycloackProvider.getRealmResource()
                .users()
                .searchByUsername(username, true);
    }

    @Override
    public String createUser(@NonNull UserDtoKeycloack userDTO) {
        int status = 0;
        UsersResource userResource = KeycloackProvider.getUserResource(null);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(userDTO.firstname());
        userRepresentation.setLastName(userDTO.lastname());
        userRepresentation.setEmail(userDTO.email());
        userRepresentation.setUsername(userDTO.username());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);

        Response response = userResource.create(userRepresentation);

        status = response.getStatus();

        if(status == 201){
            String path = response.getLocation().getPath();
            String userId = path.substring(path.lastIndexOf("/") + 1);

            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType(OAuth2Constants.PASSWORD);
            credentialRepresentation.setValue(userDTO.password());

            userResource.get(userId).resetPassword(credentialRepresentation);
        }
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public void updateUser(String userId, UserDtoKeycloack userDTO) {

    }
}
