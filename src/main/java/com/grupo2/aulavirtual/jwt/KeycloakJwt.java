package com.grupo2.aulavirtual.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;

@Getter
@Setter
public class KeycloakJwt extends JwtAuthenticationToken {

    private String firstname;
    private String lastname;

    public KeycloakJwt(Jwt jwt, Collection<? extends GrantedAuthority> authorities) {
        super(jwt, authorities);
    }
}
