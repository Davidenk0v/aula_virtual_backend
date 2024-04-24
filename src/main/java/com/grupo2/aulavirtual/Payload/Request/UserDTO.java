package com.grupo2.aulavirtual.Payload.Request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

    private AddressDTO address;

    private List<CourseDTO> courses;

    private RoleDTO role;
}
