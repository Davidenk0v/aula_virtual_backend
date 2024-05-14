package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.mappers.DtoMapper;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.LessonsEntity;
import com.grupo2.aulavirtual.entities.UserEntity;
import com.grupo2.aulavirtual.payload.request.UserDTO;
import com.grupo2.aulavirtual.payload.response.CourseResponseDto;
import com.grupo2.aulavirtual.payload.response.UserResponseDto;
import com.grupo2.aulavirtual.repositories.UserRepository;
import com.grupo2.aulavirtual.services.UserService;
import com.grupo2.aulavirtual.util.FileUtil;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    DtoMapper dtoMapper = new DtoMapper();
    FileUtil fileUtil = new FileUtil();

    private static final String ERROR = "Error";
    private static final String SAVE = "Guardado";

    @Override
    public UserEntity getLoggedUser() {
        JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String email = String.valueOf(token.getTokenAttributes().get("email"));
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Error while fetching user"));

    }

    @Override
    public void syncUser(UserEntity user) {
        if (user == null) {
            throw new EntityNotFoundException("Error while user sync");
        }

        UserEntity saveUser = user;
        Optional<UserEntity> optionalUser = userRepository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()) {
            saveUser = optionalUser.get();
            saveUser.setFirstname(user.getFirstname());
            saveUser.setLastname(user.getLastname());
        }

        userRepository.save(saveUser);
    }

    @Override
    public ResponseEntity<HashMap<String, Object>> addUser(UserDTO userDTO) {
        try {

            HashMap<String, Object> usuarios = new HashMap<>();
            UserEntity user = dtoMapper.dtoToEntity(userDTO);
            userRepository.save(user);
            usuarios.put(SAVE, userDTO);
            return ResponseEntity.status(201).body(usuarios);
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(e.getMessage(), userDTO);
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    /**
     * Metodo para distinguir entre nuevo archivo o actualizar archivo.
     * 
     * @param id   Long con la id de Lessons.
     * @param file MultiparFile con los datos del archivo.
     * @return ResponseEntity<?> con el estado de la operacion.
     */
    public ResponseEntity<?> downloadFile(Long id, MultipartFile file) {
        if (!file.isEmpty()) {
            Optional<UserEntity> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();
                if (user.getUrlImg() == null || user.getUrlImg().isEmpty()) {
                    return saveFile(user, file);
                } else {
                    return updateFile(user, file);
                }
            } else {
                return new ResponseEntity<>("No se encontro el ususario.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("No se ha enviado ningun archivo", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Metodo para guardar un archivo.
     * 
     * @param user UserEntity a la que se le hacen los cambios.
     * @param file MultiparFile con los datos del archivo.
     * @return ResponseEntity<?> con el estado de la operacion.
     */
    public ResponseEntity<?> saveFile(UserEntity user, MultipartFile file) {
        try {
            String path = fileUtil.saveFile(file, "\\Media\\User\\" + user.getUsername() + "\\files\\");
            user.setUrlImg(path);
            userRepository.save(user);
            if (path != null) {
                return new ResponseEntity<>("Se ha añadido el archivo", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        "Ocurrio un error al almacenar el archivo", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }

    }

    /**
     * Metodo para sobreescribir un archivo.
     * 
     * @param user UserEntity a la que se le hacen los cambios.
     * @param file MultiparFile con los datos del archivo.
     * @return ResponseEntity<?> con el estado de la operacion.
     */
    public ResponseEntity<?> updateFile(UserEntity user, MultipartFile file) {
        try {
            String path = fileUtil.updateFile(file, "\\Media\\User\\" + user.getUsername() + "\\files\\", user.getUrlImg());
            user.setUrlImg(path);
            userRepository.save(user);
            if (path != null) {
                return new ResponseEntity<>("Se ha añadido el archivo", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        "Ocurrio un error al almacenar el archivo", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    @Override
    public ResponseEntity<HashMap<String, Object>> findUserByEmail(String email) {
        try {
            HashMap<String, Object> usuarios = new HashMap<>();
            if (userRepository.findByEmail(email).isPresent()) {
                UserEntity user = userRepository.findByEmail(email).get();
                UserResponseDto userRespuesta = dtoMapper.entityToResponseDto(user);
                usuarios.put(SAVE, userRespuesta);
                return ResponseEntity.status(201).body(usuarios);
            } else {
                usuarios.put(ERROR, "No se encuntra este usuario con ese email");
                return ResponseEntity.status(404).body(usuarios);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(201).body(usuarios);
        }
    }

    @Override
    public ResponseEntity<HashMap<String, Object>> findUserById(Long idUser) {
        try {
            HashMap<String, Object> usuarios = new HashMap<>();
            if (userRepository.findById(idUser).isPresent()) {
                UserEntity user = userRepository.findById(idUser).get();
                UserResponseDto userRespuesta = dtoMapper.entityToResponseDto(user);
                usuarios.put(SAVE, userRespuesta);
                return ResponseEntity.status(201).body(usuarios);
            } else {
                usuarios.put(ERROR, "No se encuntra este usuario con ese id");
                return ResponseEntity.status(404).body(usuarios);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(201).body(usuarios);
        }
    }

    /**
     * Metodo para enviar un archivo al frontend.
     * 
     * @param id Long con la id del usuario.
     * @return ResponseEntity<?> con la imagen, con string en caso de error.
     */
    public ResponseEntity<?> sendFile(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (user.getUrlImg() != null || !user.getUrlImg().isEmpty()) {
                String fileRoute = user.getUrlImg();
                String extension = fileUtil.getExtensionByPath(fileRoute);
                String mediaType = fileUtil.getMediaType(extension);
                byte[] file = fileUtil.sendFile(fileRoute);
                if (file.length != 0) {
                    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(mediaType)).body(file);
                } else {
                    return new ResponseEntity<>("Ocurrio un error, el archivo puede estar corrupto.",
                            HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>("No se encontro el ususario.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("No se encuentra el archivo.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<HashMap<String, ?>> updateUser(UserDTO userDTO, Long id) {
        try {
            HashMap<String, Object> usuarios = new HashMap<>();
            if (userRepository.findById(id).isPresent()) {
                UserEntity user = userRepository.findById(id).get();
                new UserResponseDto();
                UserResponseDto userRespuesta;
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
                userRepository.save(user);
                userRespuesta = dtoMapper.entityToResponseDto(user);
                usuarios.put(SAVE, userRespuesta);
                return ResponseEntity.status(201).body(usuarios);
            } else {
                usuarios.put("No se encuentra este usuario", userDTO);
                return ResponseEntity.status(404).body(usuarios);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    @Override
    public ResponseEntity<?> userCoursesList(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("No se encontraron usuarios", HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>("No se encontraron usuarios", HttpStatus.NOT_FOUND);
        }
        List<UserResponseDto> userResponseDtos = userEntities.stream()
                .map(userEntity -> dtoMapper.entityToResponseDto(userEntity)).toList();
        return new ResponseEntity<>(userResponseDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HashMap<String, ?>> deleteUser(Long id) {
        try {
            HashMap<String, Long> response = new HashMap<>();
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                response.put("Borrado id", id);
                return ResponseEntity.status(201).body(response);
            } else {
                response.put("No se encuntra este usuario con ese id", id);
                return ResponseEntity.status(500).body(response);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(ERROR, e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    public ResponseEntity<?> setDefaultImage(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            String defaultUrlImage = fileUtil.setDefaultImage();
            if (user.getUrlImg() != null || !user.getUrlImg().isEmpty()) {
                fileUtil.deleteFile(user.getUrlImg());
                user.setUrlImg(defaultUrlImage);
                userRepository.save(user);
            } else {
                user.setUrlImg(defaultUrlImage);
                userRepository.save(user);
            }
            return new ResponseEntity<>("Se ha añadido el archivo", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontro el ususario.", HttpStatus.NOT_FOUND);
        }
    }
}
