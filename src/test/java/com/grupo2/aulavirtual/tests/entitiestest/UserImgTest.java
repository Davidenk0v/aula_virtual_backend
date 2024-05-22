package com.grupo2.aulavirtual.tests.entitiestest;

import com.grupo2.aulavirtual.entities.UserImg;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserImgTest {

    @Test
    void testGettersAndSetters() {
        UserImg userImg = UserImg.builder()
                .id(1L)
                .idUser("user123")
                .urlImg("http://example.com/image.jpg")
                .build();

        assertEquals(1L, userImg.getId(), "id getter must be equal to 1L");
        userImg.setId(2L);
        assertEquals(2L, userImg.getId(), "id setter must be equal to 2L");

        assertEquals("user123", userImg.getIdUser(), "idUser getter must be equal to 'user123'");
        userImg.setIdUser("user456");
        assertEquals("user456", userImg.getIdUser(), "idUser setter must be equal to 'user456'");

        assertEquals("http://example.com/image.jpg", userImg.getUrlImg(), "urlImg getter must be equal to 'http://example.com/image.jpg'");
        userImg.setUrlImg("http://example.com/newimage.jpg");
        assertEquals("http://example.com/newimage.jpg", userImg.getUrlImg(), "urlImg setter must be equal to 'http://example.com/newimage.jpg'");
    }

    @Test
    void testNotNull() {
        UserImg userImg = new UserImg();
        assertNotNull(userImg);
    }
}
