package com.grupo2.aulavirtual.Payload.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {

    private Long idAdress;

    private String country;

    private String number;

    private String street;

    private String city;

    private String postalCode;

    private UserResponseDto user;
}
