package com.grupo2.aulavirtual.tests.entitiestest;

import com.grupo2.aulavirtual.entities.UserImg;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserImgTest {

    @Test
    void testGettersAndSetters() {
        UserImg userImg = UserImg.builder()
                .idUser("user123")
                .urlImg("http://example.com/image.jpg")
                .build();

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
