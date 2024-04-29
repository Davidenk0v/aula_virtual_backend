package com.grupo2.aulavirtual.Payload.Response;

import java.util.List;

import lombok.*;


@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserResponseDto {

    private Long idUser;

    private String email;

    private String lastname;

    private String firstname;

    private String username;

    private AddressResponseDto address;

    private List<CourseResponseDto> courses;

    private String role;

    private String urlImg;
}
