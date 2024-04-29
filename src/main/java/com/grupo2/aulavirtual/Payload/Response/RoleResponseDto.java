package com.grupo2.aulavirtual.Payload.Response;

import java.util.List;

import com.grupo2.aulavirtual.Enum.RoleEnum;

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
