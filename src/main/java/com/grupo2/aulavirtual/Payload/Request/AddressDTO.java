package com.grupo2.aulavirtual.Payload.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long idAdress;

    private String country;

    private String number;

    private String street;

    private String city;

    private String postalCode;

    private UserDTO user;
}
