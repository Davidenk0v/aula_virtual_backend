package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.payload.request.LoginRequestDto;
import com.grupo2.aulavirtual.services.LoginService;
import com.grupo2.aulavirtual.util.KeycloakProvider;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
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
}
