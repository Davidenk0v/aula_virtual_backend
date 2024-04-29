package com.grupo2.aulavirtual.payload.keycloack;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@Value
@AllArgsConstructor
public record UserDtoKeycloack(String email, String lastname, String firstname, String username, String password,
                               Set<String> roles) {

}
