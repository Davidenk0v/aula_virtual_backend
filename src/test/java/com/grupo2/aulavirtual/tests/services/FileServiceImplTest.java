package com.grupo2.aulavirtual.tests.services;
import com.grupo2.aulavirtual.entities.UserImg;
import com.grupo2.aulavirtual.repositories.ImageRepository;
import com.grupo2.aulavirtual.services.FileService;
import com.grupo2.aulavirtual.services.KeycloakService;
import com.grupo2.aulavirtual.services.impl.FileServiceImpl;
import com.grupo2.aulavirtual.util.files.FileUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private KeycloakService keycloakService;

    @Mock
    private FileUtil fileUtil;

    @InjectMocks
    private FileService fileService = new FileServiceImpl();

    /*
    @Test
    void downloadFile_FileNotEmpty_Success() {
        // Arrange
        String userId = "user1";
        MultipartFile file = mock(MultipartFile.class);
        UserImg userImg = new UserImg();
        userImg.setUrlImg("path/to/image.jpg");

        when(file.isEmpty()).thenReturn(false);
        when(imageRepository.findByIdUser(userId)).thenReturn(Optional.of(userImg));
        when(fileUtil.saveFile(any(MultipartFile.class), anyString())).thenReturn("path/to/image.jpg");

        // Act
        ResponseEntity<?> response = fileService.downloadFile(userId, file);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("data", response.getBody());
        verify(imageRepository, times(1)).save(userImg);
    }


     */


    @Test
    void setDefaultImage_UserNotFound_NotFound() {
        // Arrange
        String userId = "user1";

        when(imageRepository.findByIdUser(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = fileService.setDefaultImage(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No se encontro el ususario.", response.getBody());
        verify(fileUtil, never()).deleteFile(anyString());
        verify(imageRepository, never()).save(any());
    }
    @Test
    void downloadFile_FileEmpty_NotFound() {
        // Arrange
        String userId = "user1";
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(true);

        // Act
        ResponseEntity<?> response = fileService.downloadFile(userId, file);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("error", response.getBody());
        verify(imageRepository, never()).save(any());
    }

    @Test
    void saveFile_Success() {
        // Arrange
        UserImg userImg = new UserImg();
        userImg.setIdUser("user1");
        MultipartFile file = mock(MultipartFile.class);

        when(fileUtil.saveFile(any(MultipartFile.class), anyString())).thenReturn("path/to/image.jpg");

        // Act
        ResponseEntity<?> response = fileService.saveFile(userImg, file);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("data", response.getBody());
        assertEquals("path/to/image.jpg", userImg.getUrlImg());
        verify(imageRepository, times(1)).save(userImg);
    }

    @Test
    void saveFile_Failure() {
        // Arrange
        UserImg userImg = new UserImg();
        userImg.setIdUser("user1");
        MultipartFile file = mock(MultipartFile.class);

        when(fileUtil.saveFile(any(MultipartFile.class), anyString())).thenReturn(null);

        // Act
        ResponseEntity<?> response = fileService.saveFile(userImg, file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("error", response.getBody());
    }

}
