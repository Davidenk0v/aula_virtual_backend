package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.entities.CategoryEntity;
import com.grupo2.aulavirtual.util.mappers.DtoMapper;
import com.grupo2.aulavirtual.payload.response.CategoryResponseDto;
import com.grupo2.aulavirtual.repositories.CategoryRepository;
import com.grupo2.aulavirtual.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository repository;

    private static final String ERROR = "Error";

    DtoMapper dtoMapper = new DtoMapper();

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> categoriesList() {
        List<CategoryEntity> categoriesList = categoryRepository.findAll();
        if (categoriesList.isEmpty()) {
            return new ResponseEntity<>("No se encontraron categorias", HttpStatus.NOT_FOUND);
        }
        List<CategoryResponseDto> categoriesResponseDtos = categoriesList.stream()
                .map(categoryEntity -> dtoMapper.entityToResponse(categoryEntity)).toList();
        return new ResponseEntity<>(categoriesResponseDtos, HttpStatus.OK);
    }

}
