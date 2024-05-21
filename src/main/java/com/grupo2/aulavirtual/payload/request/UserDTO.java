package com.grupo2.aulavirtual.payload.request;

import java.util.List;

import com.grupo2.aulavirtual.entities.CourseEntity;

import lombok.*;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String idUser;

    private String email;

    private String lastname;

    private String firstname;

    private String username;

    private String password;

    private String address;

    private List<CourseEntity> courses;

    private List<String> role;

    private String urlImg;
}
