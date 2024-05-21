package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.mappers.DtoMapper;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.UserEntity;
import com.grupo2.aulavirtual.payload.request.UserDTO;
import com.grupo2.aulavirtual.payload.response.CourseResponseDto;
import com.grupo2.aulavirtual.payload.response.UserResponseDto;
import com.grupo2.aulavirtual.repositories.UserRepository;
import com.grupo2.aulavirtual.services.FileService;
import com.grupo2.aulavirtual.services.KeycloakService;
import com.grupo2.aulavirtual.services.UserService;
import com.grupo2.aulavirtual.util.files.FileUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private KeycloakService keycloakService;

    DtoMapper dtoMapper = new DtoMapper();

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    FileUtil fileUtil = new FileUtil();

    private static final String ERROR = "error";

    private static final String DATA = "data";

    private static final String USER_NOT_FOUND = "No encontrado";

    @Value("${fileutil.default.img.user}")
    private String defaultImg;
    @Value("${fileutil.user.folder.path}")
    private String userFolder;

    @Override
    public ResponseEntity<HashMap<String, Object>> addUser(UserDTO userDTO) {
        try {
            HashMap<String, Object> usuarios = new HashMap<>();
            UserEntity user = dtoMapper.dtoToEntity(userDTO);
            if (file != null && !file.isEmpty() && user.getUrlImg() != null) {
                String path = fileUtil.saveFile(file, getCustomPath(user.getUsername()));
                user.setUrlImg(path);
            } else {
                user.setUrlImg(defaultImg);
            }
            userRepository.save(user);
            usuarios.put(DATA, userDTO);
            return ResponseEntity.status(201).body(usuarios);
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, userDTO);
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    @Override
    public ResponseEntity<?> findUserByEmail(String email) {
        try {
            HashMap<String, Object> usuarios = new HashMap<>();
            Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();
                UserResponseDto userRespuesta = dtoMapper.entityToResponseDto(user);
                return ResponseEntity.status(200).body(userRespuesta);
            } else {
                usuarios.put(ERROR, USER_NOT_FOUND);
                return ResponseEntity.status(404).body(usuarios);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    @Override
    public ResponseEntity<?> findUserByUsername(String username) {

        try {
            HashMap<String, Object> usuarios = new HashMap<>();
            Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();
                UserResponseDto userRespuesta = dtoMapper.entityToResponseDto(user);
                return ResponseEntity.status(200).body(userRespuesta);
            } else {
                usuarios.put(ERROR, USER_NOT_FOUND);
                return ResponseEntity.status(404).body(usuarios);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(201).body(usuarios);
        }
    }

    @Override
    public ResponseEntity<?> findUserById(String idUser) {
        try {
            HashMap<String, Object> usuarios = new HashMap<>();
            Optional<UserEntity> optionalUser = userRepository.findByIdKeycloak(idUser);
            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();
                UserResponseDto userRespuesta = dtoMapper.entityToResponseDto(user);
                usuarios.put(DATA, userRespuesta);
                return ResponseEntity.status(200).body(usuarios);
            } else {
                usuarios.put(ERROR, USER_NOT_FOUND);
                return ResponseEntity.status(404).body(usuarios);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }


    @Override
    public ResponseEntity<HashMap<String, ?>> updateUser(UserDTO userDTO, String idUser, MultipartFile file) {
        try {
            HashMap<String, Object> usuarios = new HashMap<>();
            Optional<UserEntity> optionalUser = userRepository.findByIdKeycloak(idUser);
            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();

                new UserResponseDto();
                UserResponseDto userRespuesta;
                if (file != null && !file.isEmpty()) {
                    String path = fileUtil.saveFile(file, "\\Media\\User\\" + user.getUsername() + "\\Image\\");
                    user.setUrlImg(path);
                }

                if (!Objects.equals(userDTO.getUsername(), "")) {
                    user.setUsername(userDTO.getUsername());
                }
                if (!Objects.equals(userDTO.getLastname(), "")) {
                    user.setLastname(userDTO.getLastname());
                }
                if (!Objects.equals(userDTO.getFirstname(), "")) {
                    user.setFirstname(userDTO.getFirstname());
                }
                if (!Objects.equals(userDTO.getUrlImg(), "")) {
                    user.setUrlImg(userDTO.getUrlImg());
                }
                if (userDTO.getAddress() != null) {
                    user.setAddress(userDTO.getAddress());
                }
                keycloakService.updateUser(user.getIdKeycloak(), userDTO); // Actualiza el usuario de la base de
                                                                           // keycloak
                userRespuesta = dtoMapper.entityToResponseDto(user);
                usuarios.put(DATA, userRespuesta);
                return ResponseEntity.status(200).body(usuarios);
            } else {
                usuarios.put(USER_NOT_FOUND, userDTO);
                return ResponseEntity.status(404).body(usuarios);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    @Override
    public ResponseEntity<?> userCoursesList(String idUser) {
        Optional<UserEntity> userOptional = userRepository.findByIdKeycloak(idUser);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(ERROR, HttpStatus.NOT_FOUND);
        }
        UserEntity user = userOptional.get();
        List<CourseEntity> coursesList = user.getCourses();
        List<CourseResponseDto> userResponseDtos = coursesList.stream()
                .map(userEntity -> dtoMapper.entityToResponseDto(userEntity)).toList();
        return new ResponseEntity<>(userResponseDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> userList() {
        List<UserEntity> userEntities = userRepository.findAll();
        if (userEntities.isEmpty()) {
            return new ResponseEntity<>(ERROR, HttpStatus.NOT_FOUND);
        }
        List<UserResponseDto> userResponseDtos = userEntities.stream()
                .map(userEntity -> dtoMapper.entityToResponseDto(userEntity)).toList();
        return new ResponseEntity<>(userResponseDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HashMap<String, ?>> deleteUser(Long id) {
        try {
            HashMap<String, Long> response = new HashMap<>();
            Optional<UserEntity> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                userRepository.deleteById(id);
                keycloakService.deleteUser(optionalUser.get().getIdKeycloak()); // Elimina al usuario de la base de
                                                                                // datos de keycloak

                logger.info("Borrado de la base de datos del keycloak y de la tabla user");
                response.put(DATA, id);

                return ResponseEntity.status(201).body(response);
            } else {
                logger.info(USER_NOT_FOUND);
                response.put(USER_NOT_FOUND, id);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            logger.error(e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    @Override
    public ResponseEntity<?> setDefaultImage(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            String defaultUrlImage = fileUtil.setDefaultImage(defaultImg);
            if (user.getUrlImg() != null || !user.getUrlImg().isEmpty()) {
                fileUtil.deleteFile(user.getUrlImg());
                user.setUrlImg(defaultUrlImage);
                userRepository.save(user);
            } else {
                user.setUrlImg(defaultUrlImage);
                userRepository.save(user);
            }
            return new ResponseEntity<>("Se elimino la imagen", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontro el ususario.", HttpStatus.NOT_FOUND);
        }
    }
}
