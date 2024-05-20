package com.grupo2.aulavirtual.payload.response;

import java.util.List;


import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleResponseDto {

    private Long idRole;

    private String role;

    private List<UserResponseDto> user;

}
