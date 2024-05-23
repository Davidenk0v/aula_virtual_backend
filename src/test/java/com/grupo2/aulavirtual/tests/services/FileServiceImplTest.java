package com.grupo2.aulavirtual.tests.services;

import com.grupo2.aulavirtual.entities.UserImg;
import com.grupo2.aulavirtual.repositories.ImageRepository;
import com.grupo2.aulavirtual.services.FileService;
import com.grupo2.aulavirtual.services.KeycloakService;
import com.grupo2.aulavirtual.services.impl.FileServiceImpl;
import com.grupo2.aulavirtual.util.files.FileUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field defaultImgField = FileServiceImpl.class.getDeclaredField("defaultImg");
        defaultImgField.setAccessible(true);
        defaultImgField.set(fileService, "defaultImage.png");
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
    void downloadFile_UserEmpty_NotFound() {
        // Arrange
        String userId = "user1";
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(imageRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = fileService.downloadFile(userId, file);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("error", response.getBody());
        verify(imageRepository, never()).save(any());
    }

    @Test
    void downloadFile_saveFile_Success() {
        // Arrange
        String userId = "user1";
        String urlImg = "image.jpg";
        UserImg userImg = new UserImg(userId, null);
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(imageRepository.findById(userId)).thenReturn(Optional.of(userImg));
        when(fileUtil.saveFile(any(MultipartFile.class), anyString())).thenReturn(urlImg);

        // Act
        ResponseEntity<?> response = fileService.downloadFile(userId, file);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("data", response.getBody());
        assertEquals(urlImg, userImg.getUrlImg());
        verify(imageRepository, times(1)).save(userImg);
    }

    @Test
    void downloadFile_saveFile_Failure() {
        // Arrange
        String userId = "user1";
        UserImg userImg = new UserImg(userId, null);
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(imageRepository.findById(userId)).thenReturn(Optional.of(userImg));
        when(fileUtil.saveFile(any(MultipartFile.class), anyString())).thenReturn(null);

        // Act
        ResponseEntity<?> response = fileService.downloadFile(userId, file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("error", response.getBody());
    }

    @Test
    void downloadFile_saveFile_Error() {
        // Arrange
        String userId = "user1";
        UserImg userImg = new UserImg(userId, null);
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(imageRepository.findById(userId)).thenReturn(Optional.of(userImg));
        when(fileUtil.saveFile(any(MultipartFile.class), anyString())).thenThrow(new RuntimeException("File saving error"));

        // Act
        ResponseEntity<?> response = fileService.downloadFile(userId, file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("File saving error", ((HashMap<String, Object>) response.getBody()).get("error"));
    }

    @Test
    void downloadFile_updateFile_Success() {
        // Arrange
        String userId = "user1";
        String urlImg = "image.jpg";
        String newUrlImg = "new image.jpg";
        UserImg userImg = new UserImg(userId, urlImg);
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(imageRepository.findById(userId)).thenReturn(Optional.of(userImg));
        when(fileUtil.updateFile(any(MultipartFile.class), anyString(), anyString(), anyString())).thenReturn(newUrlImg);

        // Act
        ResponseEntity<?> response = fileService.downloadFile(userId, file);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("data", response.getBody());
        assertEquals(newUrlImg, userImg.getUrlImg());
        verify(imageRepository, times(1)).save(userImg);
    }

    @Test
    void downloadFile_updateFile_Failure() {
        // Arrange
        String userId = "user1";
        String urlImg = "image.jpg";
        UserImg userImg = new UserImg(userId, urlImg);
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(imageRepository.findById(userId)).thenReturn(Optional.of(userImg));
        when(fileUtil.updateFile(any(MultipartFile.class), anyString(), anyString(), anyString())).thenReturn(null);

        // Act
        ResponseEntity<?> response = fileService.downloadFile(userId, file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("error", response.getBody());
    }

    @Test
    void downloadFile_updateFile_Error() {
        // Arrange
        String userId = "user1";
        String urlImg = "image.jpg";
        UserImg userImg = new UserImg(userId, urlImg);
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(imageRepository.findById(userId)).thenReturn(Optional.of(userImg));
        when(fileUtil.updateFile(any(MultipartFile.class), anyString(), anyString(), anyString())).thenThrow(new RuntimeException("File saving error"));

        // Act
        ResponseEntity<?> response = fileService.downloadFile(userId, file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("File saving error", ((HashMap<String, Object>) response.getBody()).get("error"));
    }

    @Test
    void sendFile_UserNotFoundInKeycloak() {
        // Arrange
        String userId = "user1";

        when(keycloakService.findUserById(userId)).thenReturn(null);

        // Act
        ResponseEntity<?> response = fileService.sendFile(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("error", response.getBody());
    }

    @Test
    void sendFile_UserNotFound() {
        // Arrange
        String userId = "user1";
        UserRepresentation userRepresentation = mock(UserRepresentation.class);

        when(keycloakService.findUserById(userId)).thenReturn(userRepresentation);
        when(imageRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = fileService.sendFile(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("error", response.getBody());
    }

    @Test
    void sendFile_FileNotFound() {
        // Arrange
        String userId = "user1";
        UserRepresentation userRepresentation = mock(UserRepresentation.class);
        String urlImg = "image.jpg";
        UserImg userImg = new UserImg(userId, urlImg);

        when(keycloakService.findUserById(userId)).thenReturn(userRepresentation);
        when(imageRepository.findById(userId)).thenReturn(Optional.of(userImg));
        when(fileUtil.sendFile(anyString(), anyString())).thenReturn(new byte[0]);

        // Act
        ResponseEntity<?> response = fileService.sendFile(userId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("error", response.getBody());
    }

    @Test
    void sendFile_FileFound() {
        // Arrange
        String userId = "user1";
        UserRepresentation userRepresentation = mock(UserRepresentation.class);
        String urlImg = "image.jpeg";
        UserImg userImg = new UserImg(userId, urlImg);
        byte[] fileContent = "fileContent".getBytes();

        when(keycloakService.findUserById(userId)).thenReturn(userRepresentation);
        when(imageRepository.findById(userId)).thenReturn(Optional.of(userImg));
        when(fileUtil.getExtensionByPath(anyString())).thenReturn("jpeg");
        when(fileUtil.getMediaType(anyString())).thenReturn("image/jpeg");
        when(fileUtil.sendFile(anyString(), anyString())).thenReturn(fileContent);
        
        // Act
        ResponseEntity<?> response = fileService.sendFile(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
        assertEquals(fileContent, response.getBody());
    }

    @Test
    void setDefaultImage_UserNotFound_NotFound() {
        // Arrange
        String userId = "user1";

        when(imageRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = fileService.setDefaultImage(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("error", response.getBody());
        verify(imageRepository, never()).save(any());
    }
    
    @Test
    void setDefaultImage_UserFound_ExistingImage() {
        // Arrange
        String userId = "user1";
        String urlImg = "image.jpg";
        String defaultImg = "default.jpg";
        UserImg userImg = new UserImg(userId, urlImg);

        when(imageRepository.findById(userId)).thenReturn(Optional.of(userImg));

        // Act
        ResponseEntity<?> response = fileService.setDefaultImage(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("data", response.getBody());
    }

    @Test
    void setDefaultImage_UserFound_NoExistingImage() {
        // Arrange
        String userId = "user1";
        String defaultImg = "default.jpg";
        UserImg userImg = new UserImg(userId, null);

        when(imageRepository.findById(userId)).thenReturn(Optional.of(userImg));

        // Act
        ResponseEntity<?> response = fileService.setDefaultImage(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("data", response.getBody());    
    }

    @Test
    void setDefaultImage_UserFound_Error() {
        // Arrange
        String userId = "user1";
        String urlImg = "";
        String defaultImg = "default.jpg";
        UserImg userImg = new UserImg(userId, urlImg);

        when(imageRepository.findById(userId)).thenReturn(Optional.of(userImg));
        when(imageRepository.save(userImg)).thenThrow(new RuntimeException("File saving error"));
        try {
            // Act
            fileService.setDefaultImage(userId);
        } catch (RuntimeException e) {
            // Assert
            assertEquals("File saving error", e.getMessage());
        }
    }

}
