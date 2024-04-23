package com.grupo2.aulavirtual.Payload.Response;

import java.util.List;

import com.grupo2.aulavirtual.Enum.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDto {

    private Long idRole;

    private RoleEnum role;

    private List<UserResponseDto> user;

}
