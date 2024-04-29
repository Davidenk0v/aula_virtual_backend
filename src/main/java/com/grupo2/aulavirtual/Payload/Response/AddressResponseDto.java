package com.grupo2.aulavirtual.Payload.Response;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressResponseDto {

    private Long idAdress;

    private String country;

    private String number;

    private String street;

    private String city;

    private String postalCode;

    private UserResponseDto user;
}
