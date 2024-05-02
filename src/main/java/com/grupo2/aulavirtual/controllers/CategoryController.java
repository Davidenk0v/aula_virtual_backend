package com.grupo2.aulavirtual.controllers;

import com.grupo2.aulavirtual.payload.request.CategoryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/categories")
public class CategoryController {

    // @Autowired
    // private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<?> getAllCategoryDTO() {

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {

        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDTO categoryDto) {

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDto, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddressById(@PathVariable Long id) {

        return null;
    }
}
