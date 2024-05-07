package com.grupo2.aulavirtual.tests.responsedto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressResponseDtoTest {
    AddressResponseDto addressResponseDto;

    @BeforeEach
    void setUp() {
        addressResponseDto = AddressResponseDto.builder()
                .idAdress(1L)
                .country("España")
                .number("25")
                .city("Barcelona")
                .postalCode("08001")
                .user(null)
                .street("Calle 1").build();
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, addressResponseDto.getIdAdress(), "idAdress getter must be equal to 1L");
        addressResponseDto.setIdAdress(2L);
        assertEquals(2L, addressResponseDto.getIdAdress(), "idAdress setter must be equal to 2L");

        assertEquals("España", addressResponseDto.getCountry(), "Country getter must be equal to \"España\"");
        addressResponseDto.setCountry("France");
        assertEquals("France", addressResponseDto.getCountry(), "Country setter must be equal to \"France\"");

        assertEquals("Barcelona", addressResponseDto.getCity(), "City getter must be equal to \"Barcelona\"");
        addressResponseDto.setCity("Madrid");
        assertEquals("Madrid", addressResponseDto.getCity(), "City setter must be equal to \"Madrid\"");

        assertEquals("Calle 1", addressResponseDto.getStreet(), "Street getter must be equal to \"Calle 1\"");
        addressResponseDto.setStreet("Avenida 2");
        assertEquals("Avenida 2", addressResponseDto.getStreet(), "Street setter must be equal to \"Avenida 2\"");

        assertEquals("25", addressResponseDto.getNumber(), "Number getter must be equal to \"25\"");
        addressResponseDto.setNumber("30");
        assertEquals("30", addressResponseDto.getNumber(), "Number setter must be equal to \"30\"");

        assertEquals("08001", addressResponseDto.getPostalCode(), "Postal code getter must be equal to \"08001\"");
        addressResponseDto.setPostalCode("08002");
        assertEquals("08002", addressResponseDto.getPostalCode(), "Postal code setter must be equal to \"08002\"");

    }

    @Test
    void testNotNull() {
        AddressResponseDto addressResponseDto = new AddressResponseDto();
        assertNotNull(addressResponseDto);
    }
}