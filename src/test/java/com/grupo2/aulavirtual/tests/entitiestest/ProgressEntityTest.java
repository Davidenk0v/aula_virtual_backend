package com.grupo2.aulavirtual.tests.entitiestest;

import com.grupo2.aulavirtual.entities.ProgressEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgressEntityTest {

    @Test
    void testGettersAndSetters() {
        ProgressEntity progressEntity = ProgressEntity.builder()
                .id(1L)
                .idKeyCloak("12345-abcde")
                .idCourse(101L)
                .points(85.5f)
                .progress(75.0f)
                .build();

        assertEquals(1L, progressEntity.getId(), "id getter must be equal to 1L");
        progressEntity.setId(2L);
        assertEquals(2L, progressEntity.getId(), "id setter must be equal to 2L");

        assertEquals("12345-abcde", progressEntity.getIdKeyCloak(), "idKeyCloak getter must be equal to '12345-abcde'");
        progressEntity.setIdKeyCloak("67890-fghij");
        assertEquals("67890-fghij", progressEntity.getIdKeyCloak(), "idKeyCloak setter must be equal to '67890-fghij'");

        assertEquals(101L, progressEntity.getIdCourse(), "idCourse getter must be equal to 101L");
        progressEntity.setIdCourse(202L);
        assertEquals(202L, progressEntity.getIdCourse(), "idCourse setter must be equal to 202L");

        assertEquals(85.5f, progressEntity.getPoints(), "points getter must be equal to 85.5f");
        progressEntity.setPoints(95.0f);
        assertEquals(95.0f, progressEntity.getPoints(), "points setter must be equal to 95.0f");

        assertEquals(75.0f, progressEntity.getProgress(), "progress getter must be equal to 75.0f");
        progressEntity.setProgress(80.0f);
        assertEquals(80.0f, progressEntity.getProgress(), "progress setter must be equal to 80.0f");
    }

    @Test
    void testNotNull() {
        ProgressEntity progressEntity = new ProgressEntity();
        assertNotNull(progressEntity);
    }
}
