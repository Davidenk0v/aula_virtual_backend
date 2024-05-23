package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.entities.UserImg;
import com.grupo2.aulavirtual.repositories.ImageRepository;
import com.grupo2.aulavirtual.services.FileService;
import com.grupo2.aulavirtual.services.KeycloakService;
import com.grupo2.aulavirtual.util.files.FileUtil;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    KeycloakService keycloakService;

    FileUtil fileUtil = new FileUtil();

    private static final String ERROR = "error";

    private static final String DATA = "data";

    @Value("${fileutil.default.img.user}")
    private String defaultImg;
    @Value("${fileutil.user.folder.path}")
    private String userFolder;

    @Override
    public ResponseEntity<?> downloadFile(String id, MultipartFile file) {
        if (!file.isEmpty()) {
            Optional<UserImg> optionalImg = imageRepository.findById(id);
            if (optionalImg.isPresent()) {
                UserImg user = optionalImg.get();
                if (user.getUrlImg() == null || user.getUrlImg().isEmpty()) {
                    return saveFile(user, file);
                } else {
                    return updateFile(user, file);
                }
            } else {
                return new ResponseEntity<>(ERROR, HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(ERROR, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    public ResponseEntity<?> saveFile(UserImg user, MultipartFile file) {
        try {
            String path = fileUtil.saveFile(file, getCustomPath(user.getIdUser()));
            user.setUrlImg(path);
            imageRepository.save(user);
            if (path != null) {
                return new ResponseEntity<>(DATA, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }

    }

    @Override
    public ResponseEntity<?> updateFile(UserImg user, MultipartFile file) {
        try {
            String path = fileUtil.updateFile(file, getCustomPath(user.getIdUser()),
                    getCustomPath(user.getIdUser()) + user.getUrlImg(), defaultImg);
            user.setUrlImg(path);
            imageRepository.save(user);
            if (path != null) {
                return new ResponseEntity<>(DATA, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    @Override
    public ResponseEntity<?> sendFile(String id) {

        UserRepresentation userRepresentation = keycloakService.findUserById(id);
        if (userRepresentation == null) {
            return new ResponseEntity<>(ERROR, HttpStatus.NOT_FOUND);
        }

        UserImg user;
        Optional<UserImg> optionalUserImg = imageRepository.findById(id);

        if (optionalUserImg.isPresent()) {
            user = optionalUserImg.get();
        } else {
            user = new UserImg();
            user.setIdUser(id);
            setDefaultImage(id);
        }
        if (user.getUrlImg() != null && !user.getUrlImg().isEmpty()) {
            String fileRoute = getCustomPath(user.getIdUser()) + user.getUrlImg();
            String extension = fileUtil.getExtensionByPath(fileRoute);
            String mediaType = fileUtil.getMediaType(extension);
            byte[] file = fileUtil.sendFile(fileRoute, defaultImg);
            if (file.length != 0) {
                imageRepository.save(user);
                return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(mediaType)).body(file);
            } else {
                return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(ERROR, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> setDefaultImage(String id) {
        Optional<UserImg> optionalUser = imageRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserImg user = optionalUser.get();
            if (user.getUrlImg() != null && !user.getUrlImg().isEmpty()) {
                fileUtil.deleteFile(getCustomPath(user.getIdUser()) + user.getUrlImg(), defaultImg);
                user.setUrlImg(defaultImg);
                imageRepository.save(user);
            } else {
                user.setUrlImg(defaultImg);
                imageRepository.save(user);
            }
            return new ResponseEntity<>(DATA, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ERROR, HttpStatus.NOT_FOUND);
        }
    }

    private String getCustomPath(String courseNane) {
        return userFolder + courseNane + "\\Image\\";
    }

}
