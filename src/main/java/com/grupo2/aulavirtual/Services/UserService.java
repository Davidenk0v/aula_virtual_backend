package com.grupo2.aulavirtual.Services;

import com.grupo2.aulavirtual.Config.Mappers.DtoMapper;
import com.grupo2.aulavirtual.Entities.UserEntity;
import com.grupo2.aulavirtual.Payload.Request.UserDTO;
import com.grupo2.aulavirtual.Payload.Response.UserResponseDto;
import com.grupo2.aulavirtual.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    DtoMapper dtoMapper = new DtoMapper();
    public ResponseEntity<HashMap<String, Object>> crearUsuario(UserDTO userDTO) {
        try {
            HashMap<String, Object> usuarios = new HashMap<>();
            userRepository.save(dtoMapper.dtoToEntity(userDTO));
            usuarios.put("Guardado",userDTO);
            return ResponseEntity.status(201).body(usuarios);
        }catch (Exception e){
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put(e.getMessage(),userDTO);
            return ResponseEntity.status(201).body(usuarios);
        }
    }

    public ResponseEntity<HashMap<String, Object>> buscarUsuario(String email) {
        try {
            HashMap<String, Object> usuarios = new HashMap<>();
            if(userRepository.findByEmail(email).isPresent()){
                UserEntity user = userRepository.findByEmail(email).get();
                UserResponseDto userRespuesta = dtoMapper.entityToResponseDto(user);
                usuarios.put("Guardado",userRespuesta);
                return ResponseEntity.status(201).body(usuarios);
            }else {
                usuarios.put("Error","No se encuntra este usuario");
                return ResponseEntity.status(404).body(usuarios);
            }
        }catch (Exception e){
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error",e.getMessage());
            return ResponseEntity.status(201).body(usuarios);
        }
    }

    public ResponseEntity<HashMap<String, ?>> listaUsuarios() {
        try {
            HashMap<String, List<UserResponseDto>> usuarios = new HashMap<>();
            return  ResponseEntity.status(201).body(usuarios);
        }catch (Exception e){
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error",e.getMessage());
            return ResponseEntity.status(500).body(usuarios);
        }
    }

    public ResponseEntity<HashMap<String, ?>> editarUsuario(UserDTO userDTO, String email) {
        try {
            HashMap<String, Object> usuarios = new HashMap<>();
            if(userRepository.findByEmail(email).isPresent()){
                UserEntity user = userRepository.findByEmail(email).get();
                UserResponseDto userRespuesta = new UserResponseDto();
                if (userDTO.getUsername()!=""){
                    user.setUsername(userDTO.getUsername());
                }
                if (userDTO.getLastname()!=""){
                    user.setLastname(userDTO.getLastname());
                }
                if (userDTO.getFirstname()!=""){
                    user.setFirstname(userDTO.getFirstname());
                }
                if (userDTO.getUrlImg()!=""){
                    user.setUrlImg(userDTO.getUrlImg());
                }
                if (userDTO.getAddress()!=null){
                    if(userDTO.getAddress().getCity()!=""){
                        user.getAdress().setCity(userDTO.getAddress().getCity());
                    }
                    if(userDTO.getAddress().getCountry()!=""){
                        user.getAdress().setCountry(userDTO.getAddress().getCountry());
                    }
                    if(userDTO.getAddress().getPostalCode()!=""){
                        user.getAdress().setPostalCode(userDTO.getAddress().getPostalCode());
                    }
                    if(userDTO.getAddress().getStreet()!=""){
                        user.getAdress().setStreet(userDTO.getAddress().getStreet());
                    }
                    if(userDTO.getAddress().getNumber()!=""){
                        user.getAdress().setNumber(userDTO.getAddress().getNumber());
                    }
                }
                userRepository.save(user);
                userRespuesta = dtoMapper.entityToResponseDto(user);
                usuarios.put("Guardado",userRespuesta);
                return ResponseEntity.status(201).body(usuarios);
            }else {
                usuarios.put("No se encuentra este usuario",userDTO);
                return ResponseEntity.status(404).body(usuarios);
            }
        }catch (Exception e){
            HashMap<String, Object> usuarios = new HashMap<>();
            usuarios.put("Error",e.getMessage());
            return ResponseEntity.status(201).body(usuarios);
        }
    }


}
