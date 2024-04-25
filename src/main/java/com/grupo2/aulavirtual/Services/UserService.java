package com.grupo2.aulavirtual.Services;

import com.grupo2.aulavirtual.Config.Mappers.DtoMapper;
import com.grupo2.aulavirtual.Entities.RoleEntity;
import com.grupo2.aulavirtual.Entities.UserEntity;
import com.grupo2.aulavirtual.Enum.RoleEnum;
import com.grupo2.aulavirtual.Payload.Request.UserDTO;
import com.grupo2.aulavirtual.Payload.Response.UserResponseDto;
import com.grupo2.aulavirtual.Repository.RoleRepository;
import com.grupo2.aulavirtual.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    DtoMapper dtoMapper = new DtoMapper();

    public ResponseEntity<HashMap<String, Object>> addUser(UserDTO userDTO) {
        try {
            RoleEntity role;
            if (!roleRepository.existsByRole(userDTO.getRole().getRole())){
                 role = new RoleEntity();
                role.setRole(userDTO.getRole().getRole());
                roleRepository.save(role);
            }else {
                 role = roleRepository.findByRole(userDTO.getRole().getRole()).get();
            }
            HashMap<String, Object> usuarios = new HashMap<>();
            UserEntity user = dtoMapper.dtoToEntity(userDTO);
            user.setRole(role);
            userRepository.save(user);
            usuarios.put("Guardado", userDTO);
            logger.info("llega");
            System.out.println(usuarios + "ha sido guardado ");
            return ResponseEntity.status(201).body(usuarios);
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(e.getMessage(), userDTO);
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    public ResponseEntity<HashMap<String, Object>> findUserByEmail(String email) {
        try {
            HashMap<String, Object> usuarios = new HashMap<>();
            if (userRepository.findByEmail(email).isPresent()) {
                UserEntity user = userRepository.findByEmail(email).get();
                UserResponseDto userRespuesta = dtoMapper.entityToResponseDto(user);
                usuarios.put("Guardado", userRespuesta);
                return ResponseEntity.status(201).body(usuarios);
            } else {
                usuarios.put("Error", "No se encuntra este usuario con ese email");
                return ResponseEntity.status(404).body(usuarios);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(201).body(usuarios);
        }
    }

    public ResponseEntity<HashMap<String, Object>> findUserById(Long idUser) {
        try {
            HashMap<String, Object> usuarios = new HashMap<>();
            if (userRepository.findById(idUser).isPresent()) {
                UserEntity user = userRepository.findById(idUser).get();
                UserResponseDto userRespuesta = dtoMapper.entityToResponseDto(user);
                usuarios.put("Guardado", userRespuesta);
                return ResponseEntity.status(201).body(usuarios);
            } else {
                usuarios.put("Error", "No se encuntra este usuario con ese id");
                return ResponseEntity.status(404).body(usuarios);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(201).body(usuarios);
        }
    }

    public ResponseEntity<HashMap<String, ?>> updateUser(UserDTO userDTO, Long id) {
        try {
            HashMap<String, Object> usuarios = new HashMap<>();
            if (userRepository.findById(id).isPresent()) {
                UserEntity user = userRepository.findById(id).get();
                UserResponseDto userRespuesta = new UserResponseDto();
                if (userDTO.getUsername() != "") {
                    user.setUsername(userDTO.getUsername());
                }
                if (userDTO.getLastname() != "") {
                    user.setLastname(userDTO.getLastname());
                }
                if (userDTO.getFirstname() != "") {
                    user.setFirstname(userDTO.getFirstname());
                }
                if (userDTO.getUrlImg() != "") {
                    user.setUrlImg(userDTO.getUrlImg());
                }
                if (userDTO.getAddress() != null) {
                    if (userDTO.getAddress().getCity() != "") {
                        user.getAdress().setCity(userDTO.getAddress().getCity());
                    }
                    if (userDTO.getAddress().getCountry() != "") {
                        user.getAdress().setCountry(userDTO.getAddress().getCountry());
                    }
                    if (userDTO.getAddress().getPostalCode() != "") {
                        user.getAdress().setPostalCode(userDTO.getAddress().getPostalCode());
                    }
                    if (userDTO.getAddress().getStreet() != "") {
                        user.getAdress().setStreet(userDTO.getAddress().getStreet());
                    }
                    if (userDTO.getAddress().getNumber() != "") {
                        user.getAdress().setNumber(userDTO.getAddress().getNumber());
                    }
                }
                userRepository.save(user);
                userRespuesta = dtoMapper.entityToResponseDto(user);
                usuarios.put("Guardado", userRespuesta);
                return ResponseEntity.status(201).body(usuarios);
            } else {
                usuarios.put("No se encuentra este usuario", userDTO);
                return ResponseEntity.status(404).body(usuarios);
            }
        } catch (Exception e) {
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(201).body(usuarios);
        }
    }

    public ResponseEntity<?> userList() {
       List<UserEntity> userEntities = userRepository.findAll();
       if(userEntities.isEmpty()){
           return new ResponseEntity<>("No se encontraron usuarios", HttpStatus.NOT_FOUND);
       }
        List<UserResponseDto> userResponseDtos = userEntities.stream().map(userEntity -> dtoMapper.entityToResponseDto(userEntity)).toList();
        return new ResponseEntity<>(userResponseDtos, HttpStatus.OK);
    }

    public ResponseEntity<HashMap<String, ?>> deleteUser(Long id){
        try {
            HashMap<String, Long> response = new HashMap<>();
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                response.put("Borrado id", id);
                return ResponseEntity.status(201).body(response);
            }else {
                response.put("No se encuntra este usuario con ese id", id);
                return ResponseEntity.status(500).body(response);
            }
        }catch (Exception e){
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error", e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }
}
