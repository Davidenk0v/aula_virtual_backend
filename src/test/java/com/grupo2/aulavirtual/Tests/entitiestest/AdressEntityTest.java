package com.grupo2.aulavirtual.tests.entitiestest;

import com.grupo2.aulavirtual.Entities.AdressEntity;
import com.grupo2.aulavirtual.Payload.Request.AddressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdressEntityTest {

    AdressEntity addressEntity;

    @BeforeEach
    void setUp() {
        addressEntity = AdressEntity.builder()
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
        assertEquals(1L, addressEntity.getIdAdress(), "idAdress getter must be equal to 1L");
        addressEntity.setIdAdress(2L);
        assertEquals(2L, addressEntity.getIdAdress(), "idAdress setter must be equal to 2L");

        assertEquals("España", addressEntity.getCountry(), "Country getter must be equal to \"España\"");
        addressEntity.setCountry("France");
        assertEquals("France", addressEntity.getCountry(), "Country setter must be equal to \"France\"");

        assertEquals("Barcelona", addressEntity.getCity(), "City getter must be equal to \"Barcelona\"");
        addressEntity.setCity("Madrid");
        assertEquals("Madrid", addressEntity.getCity(), "City setter must be equal to \"Madrid\"");

        assertEquals("Calle 1", addressEntity.getStreet(), "Street getter must be equal to \"Calle 1\"");
        addressEntity.setStreet("Avenida 2");
        assertEquals("Avenida 2", addressEntity.getStreet(), "Street setter must be equal to \"Avenida 2\"");

        assertEquals("25", addressEntity.getNumber(), "Number getter must be equal to \"25\"");
        addressEntity.setNumber("30");
        assertEquals("30", addressEntity.getNumber(), "Number setter must be equal to \"30\"");

        assertEquals("08001", addressEntity.getPostalCode(), "Postal code getter must be equal to \"08001\"");
        addressEntity.setPostalCode("08002");
        assertEquals("08002", addressEntity.getPostalCode(), "Postal code setter must be equal to \"08002\"");

    }

    @Test
    void testNotNull() {
        AdressEntity address = new AdressEntity();
        assertNotNull(address);
    }
}