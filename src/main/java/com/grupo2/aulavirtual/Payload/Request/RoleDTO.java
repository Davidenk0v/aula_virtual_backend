package com.grupo2.aulavirtual.Payload.Request;

import java.util.List;

import com.grupo2.aulavirtual.Entities.UserEntity;
import com.grupo2.aulavirtual.Enum.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private Long idRole;

    private RoleEnum role;

    private List<UserEntity> user;
}
