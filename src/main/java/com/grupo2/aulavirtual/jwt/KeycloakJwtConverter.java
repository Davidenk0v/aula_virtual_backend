package com.grupo2.aulavirtual.jwt;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakJwtConverter implements Converter<Jwt, KeycloakJwt> {
    @Override
    public KeycloakJwt convert(@NonNull Jwt jwt) {
        // Extract claims and authorities as needed
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);

        // You can also map other information from the Jwt to the custom token
        var customJwt = new KeycloakJwt(jwt, authorities);
        customJwt.setFirstname(jwt.getClaimAsString("given_name"));
        customJwt.setLastname(jwt.getClaimAsString("family_name"));
        return customJwt;
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        var authorities = new ArrayList<GrantedAuthority>();

        Map<String, Object> realm_access = jwt.getClaimAsMap("realm_access");
        if (realm_access != null && realm_access.get("roles") != null) {
            var roles = realm_access.get("roles");
            if (roles instanceof List l) {
                l.forEach(role ->
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + role))
                );
            }
        }

        return authorities;
    }
}
