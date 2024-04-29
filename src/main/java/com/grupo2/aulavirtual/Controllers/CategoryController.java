package com.grupo2.aulavirtual.Controllers;

import com.grupo2.aulavirtual.Entities.CategoryEntity;
import com.grupo2.aulavirtual.Payload.Request.CategoryDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/categories")
@Tag(name = "Categories API", description = "API REST para la gestión de categorias")
public class CategoryController {


    @Operation(summary = "Get all categories", tags = "Categories API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<?> getAllCategoriesDTO() {
        return null;
    }

    @Operation(summary = "Get a category by ID", tags = "Categories API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryEntity.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        return null;
    }

    @Operation(summary = "Create a new category", tags = "Categories API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDTO categoryDTO) {
        return null;
    }

    @Operation(summary = "Update a category by ID", tags = "Categories API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryEntity.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO category, @PathVariable Long id) {
        return null;
    }

    @Operation(summary = "Delete a category by ID", tags = "Categories API")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        return null;
    }
}


