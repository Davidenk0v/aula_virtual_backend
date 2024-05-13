package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.UserEntity;
import com.grupo2.aulavirtual.mappers.DtoMapper;
import com.grupo2.aulavirtual.payload.request.CategoryDTO;
import com.grupo2.aulavirtual.payload.request.CourseDTO;
import com.grupo2.aulavirtual.payload.response.CourseResponseDto;
import com.grupo2.aulavirtual.payload.response.UserResponseDto;
import com.grupo2.aulavirtual.repositories.CategoryRepository;
import com.grupo2.aulavirtual.repositories.CourseRepository;
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
    private CourseRepository courseRepository;

    @Override
    public ResponseEntity<?> categoriesList() {
        List<CourseEntity> categoriesList = courseRepository.findAll();
        if (categoriesList.isEmpty()) {
            return new ResponseEntity<>("No se encontraron categorias", HttpStatus.NOT_FOUND);
        }
        List<CourseResponseDto> categoiesResponseDtos = categoriesList.stream()
                .map(courseEntity -> dtoMapper.entityToResponseDto(courseEntity)).toList();
        return new ResponseEntity<>(categoiesResponseDtos, HttpStatus.OK);
    }

}
