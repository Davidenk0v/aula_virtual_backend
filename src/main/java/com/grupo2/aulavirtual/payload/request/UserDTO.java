package com.grupo2.aulavirtual.payload.request;

import java.util.List;

import com.grupo2.aulavirtual.entities.AdressEntity;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.RoleEntity;

import lombok.*;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long idUser;

    private String email;

    private String lastname;

    private String firstname;

    private String username;

    private String password;

    private AdressEntity address;

    private List<CourseEntity> courses;

    private RoleEntity role;

    private String urlImg;
}
