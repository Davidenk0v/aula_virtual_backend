package com.grupo2.aulavirtual.Controllers;

import com.grupo2.aulavirtual.Payload.Request.RoleDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/roles")
@Tag(name = "Roles API", description = "API REST para la gestión de roles")
public class RoleController {


    @Operation(summary = "Obtener todas los roles", tags = "Roles API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación realizada con éxito", content = @Content),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<?> getAllRolesDTO() {
        return null;
    }

    @Operation(summary = "Obtener un rol por ID", tags = "Roles API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operación realizada con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {

        return null;
    }

    @Operation(summary = "Crear un nuevo rol", tags = "Roles API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Rol creado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<?> saveRole(@RequestBody RoleDTO role) {

        return null;
    }

    @Operation(summary = "Actualizar un rol por ID", tags = "Roles API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol actualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@RequestBody RoleDTO role, @PathVariable Long id) {
        return null;
    }

    @Operation(summary = "Eliminar un rol por ID", tags = "Roles API")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Rol eliminado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoleById(@PathVariable Long id) {
        return null;
    }
}


