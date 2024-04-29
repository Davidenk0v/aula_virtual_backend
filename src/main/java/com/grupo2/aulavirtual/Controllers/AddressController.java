package com.grupo2.aulavirtual.Controllers;

import java.util.List;

import com.grupo2.aulavirtual.Payload.Request.AddressDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/address")
@Tag(name = "Addresses API", description = "API REST para la gestión de direccion")
public class AddressController {

    // @Autowired
    // private AddressService addressService;

    @Operation(summary = "Get all addresses", tags = "Addresses API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<?> getAllAddressesDTO() {
        return null;
    }

    @Operation(summary = "Get an address by ID", tags = "Addresses API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable Long id) {
        return null;
    }

    @Operation(summary = "Create a new address", tags = "Addresses API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Address created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<?> saveAddress(@RequestBody AddressDTO addressDTO) {
        return null;
    }

    @Operation(summary = "Update an address by ID", tags = "Addresses API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(@RequestBody AddressDTO address, @PathVariable Long id) {
        return null;
    }

    @Operation(summary = "Delete an address by ID", tags = "Addresses API")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddressById(@PathVariable Long id) {
        return null;
    }
}