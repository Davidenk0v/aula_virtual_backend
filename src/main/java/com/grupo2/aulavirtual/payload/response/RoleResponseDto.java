package com.grupo2.aulavirtual.payload.response;

import java.util.List;

import com.grupo2.aulavirtual.entities.enums.RoleEnum;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleResponseDto {

    private Long idRole;

    private RoleEnum role;

    private List<UserResponseDto> user;

}
