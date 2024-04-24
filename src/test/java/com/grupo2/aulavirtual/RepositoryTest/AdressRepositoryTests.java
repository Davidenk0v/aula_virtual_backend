package com.grupo2.aulavirtual.RepositoryTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.grupo2.aulavirtual.Entities.AdressEntity;
import com.grupo2.aulavirtual.Repository.AddressRepository;


@DataJpaTest
class AdressRepositoryTests {

    @MockBean
    private AddressRepository addressRepository;

    private AdressEntity addressEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        addressEntity = AdressEntity.builder()
                .idAdress(1L)
                .country("España")
                .city("Barcelona")
                .street("Calle 1")
                .number("25")
                .postalCode("08001")
                .build();
    }

    @Test
    public void saveAddressTest() {
        // Arrange
        when(addressRepository.save(addressEntity)).thenReturn(addressEntity);

        // Act
        AdressEntity savedAddress = addressRepository.save(addressEntity);

        // Assert
        assertEquals(addressEntity, savedAddress, "Saved address should match the original address");
    }

    @Test
    public void deleteAddressTest() {
        // Arrange
        when(addressRepository.existsById(addressEntity.getIdAdress())).thenReturn(true);

        // Act
        boolean addressExists = addressRepository.existsById(addressEntity.getIdAdress());
        addressRepository.deleteById(addressEntity.getIdAdress());

        // Assert
        assertTrue(addressExists, "Address should exist before deletion");
        verify(addressRepository, times(1)).existsById(addressEntity.getIdAdress());
        verify(addressRepository, times(1)).deleteById(addressEntity.getIdAdress());
    }

    // Test entidades concreta
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
    void testDataAnnotation() {
        AdressEntity addressEntity2 = AdressEntity.builder()
                .idAdress(1L)
                .country("España")
                .city("Barcelona")
                .street("Calle 1")
                .number("25")
                .postalCode("08001")
                .build();

        assertEquals(addressEntity.hashCode(), addressEntity2.hashCode(), "Hashcode must be equal");
        assertEquals(addressEntity, addressEntity2, "Objects must be equal");

        assertEquals(addressEntity2.toString(), addressEntity.toString(), "toString must be equal");
    }

    @Test
    void testBuilder() {
        AdressEntity addressEntity2 = AdressEntity.builder()
                .idAdress(1L)
                .country("España")
                .city("Barcelona")
                .street("Calle 1")
                .number("25")
                .postalCode("08001")
                .build();

        assertEquals(addressEntity.hashCode(), addressEntity2.hashCode(), "Hashcode must be equal");
        assertEquals(addressEntity, addressEntity2, "Objects must be equal");

        assertEquals(addressEntity2.toString(), addressEntity.toString(), "toString must be equal");
    }
}
