package com.grupo2.aulavirtual.payload.request;

import java.util.List;

import com.grupo2.aulavirtual.entities.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleDTO {

    private Long idRole;

    private String role;

    private List<UserEntity> user;
}
