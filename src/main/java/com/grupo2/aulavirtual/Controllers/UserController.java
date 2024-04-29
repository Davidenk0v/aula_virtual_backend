package com.grupo2.aulavirtual.Controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.grupo2.aulavirtual.Entities.UserEntity;
import com.grupo2.aulavirtual.Payload.Request.UserDTO;
import com.grupo2.aulavirtual.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/users")
@Tag(name = "Users API", description = "API REST para la gestión de usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Obtener todos los usuarios", tags = "Users API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación realizada con éxito", content = @Content),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<?> getAllUserDTO() {
        return userService.userList();
    }

    @Operation(summary = "Obtener un usuario por ID", tags = "Users API")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operación realizada con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntity.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @Operation(summary = "Crear un nuevo usuario", tags = "Users API")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntity.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) {
        return userService.addUser(user);
    }

    @Operation(summary = "Actualizar un usuario por ID", tags = "Users API")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntity.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO user, @PathVariable Long id) {
        return userService.updateUser(user, id);
    }

    @Operation(summary = "Eliminar un usuario por ID", tags = "Users API")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}


