package com.grupo2.aulavirtual.mappers;

import java.util.List;

import com.grupo2.aulavirtual.payload.request.*;
import jakarta.persistence.Column;
import org.modelmapper.ModelMapper;

import com.grupo2.aulavirtual.entities.CategoryEntity;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.LessonsEntity;
import com.grupo2.aulavirtual.entities.RoleEntity;
import com.grupo2.aulavirtual.entities.SubjectsEntity;
import com.grupo2.aulavirtual.entities.UserEntity;
import com.grupo2.aulavirtual.payload.response.CategoryResponseDto;
import com.grupo2.aulavirtual.payload.response.CourseResponseDto;
import com.grupo2.aulavirtual.payload.response.LessonsResponseDto;
import com.grupo2.aulavirtual.payload.response.RoleResponseDto;
import com.grupo2.aulavirtual.payload.response.SubjectsResponseDto;
import com.grupo2.aulavirtual.payload.response.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    ModelMapper modelMapper = new ModelMapper();


    public CategoryEntity dtoToEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, CategoryEntity.class);
    }

    public CourseEntity dtoToEntity(CourseDTO courseDTO) {
        return modelMapper.map(courseDTO, CourseEntity.class);
    }

    public SubjectsEntity dtoToEntity(SubjectDTO subjectDTO) {
        return modelMapper.map(subjectDTO, SubjectsEntity.class);
    }

    public LessonsEntity dtoToEntity(LessonsDTO lessonsDTO) {
        return modelMapper.map(lessonsDTO, LessonsEntity.class);
    }

    public RoleEntity dtoToEntity(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, RoleEntity.class);
    }

    public UserEntity dtoToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }


    public CategoryResponseDto entityToResponse(CategoryEntity categoryEntity) {

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto()
                .builder()
                .idCategory(categoryEntity.getIdCategory())
                .category(categoryEntity.getCategory())
                .build();

        if (categoryEntity.getCourse() != null) {
            List<CourseResponseDto> courseResponseDto = categoryEntity.getCourse().stream()
                    .map(course -> modelMapper.map(course, CourseResponseDto.class)).toList();

            categoryResponseDto.setCourse(courseResponseDto);
        }
        return categoryResponseDto;

    }

    public CourseResponseDto entityToResponseDto(CourseEntity courseEntity) {

        CourseResponseDto courseResponseDto = new CourseResponseDto()
                .builder()
                .idCourse(courseEntity.getIdCourse())
                .name(courseEntity.getName())
                .description(courseEntity.getDescription())
                .startDate(courseEntity.getStartDate())
                .finishDate(courseEntity.getFinishDate())
                .price(courseEntity.getPago())
                .build();

        if (courseEntity.getUser() != null) {
            List<UserResponseDto> userResponseDto = courseEntity.getUser().stream()
                    .map(user -> modelMapper.map(user, UserResponseDto.class)).toList();

            courseResponseDto.setUsers(userResponseDto);
        }
        return courseResponseDto;

    }

    public LessonsResponseDto entityToResponseDto(LessonsEntity lessonsEntity) {

        SubjectsResponseDto subjectsResponseDto = modelMapper.map(lessonsEntity.getSubject(),
                SubjectsResponseDto.class);
        LessonsResponseDto lessonsResponseDto = new LessonsResponseDto()
                .builder()
                .idLesson(lessonsEntity.getIdLesson())
                .name(lessonsEntity.getName())
                .contenido(lessonsEntity.getContenido())
                .description(lessonsEntity.getDescription())
                .build();

        if (lessonsEntity.getSubject() != null) {
            lessonsResponseDto.setSubject(subjectsResponseDto);
        }
        return lessonsResponseDto;

    }

    public RoleResponseDto entityToResponseDto(RoleEntity roleEntity) {

        RoleResponseDto roleResponseDto = new RoleResponseDto()
                .builder()
                .idRole(roleEntity.getIdRole())
                .role(roleEntity.getRole())
                .build();

        if (roleEntity.getUser() != null) {
            List<UserResponseDto> usersResponseDto = roleEntity.getUser().stream()
                    .map(user -> modelMapper.map(user, UserResponseDto.class)).toList();

            roleResponseDto.setUser(usersResponseDto);
        }
        return roleResponseDto;

    }

    public SubjectsResponseDto entityToResponseDto(SubjectsEntity subjectsEntity) {

        SubjectsResponseDto subjectsResponse = new SubjectsResponseDto()
                .builder()
                .idSubject(subjectsEntity.getIdSubject())
                .name(subjectsEntity.getName())
                .description(subjectsEntity.getDescription())
                .build();

        if (subjectsEntity.getCourse() != null) {
            CourseResponseDto courseResponseDto = modelMapper.map(subjectsEntity.getCourse(),
                    CourseResponseDto.class);
            subjectsResponse.setCourse(courseResponseDto);
        }

        if (subjectsEntity.getLessons() != null) {
            List<LessonsResponseDto> lessonsResponseDto = subjectsEntity.getLessons().stream()
                    .map(lessons -> modelMapper.map(lessons, LessonsResponseDto.class)).toList();

            subjectsResponse.setLessons(lessonsResponseDto);
        }
        return subjectsResponse;

    }

    public UserResponseDto entityToResponseDto(UserEntity userEntity) {

        UserResponseDto userResponseDto = new UserResponseDto()
                .builder()
                .idUser(userEntity.getIdUser())
                .email(userEntity.getEmail())
                .lastname(userEntity.getLastname())
                .firstname(userEntity.getFirstname())
                .username(userEntity.getUsername())
                .address(userEntity.getAddress())
                .role(userEntity.getRole().stream().map(role->role.getRole().name()).toList())
                .build();

        if (userEntity.getCourses() != null) {
            List<CourseResponseDto> courseResponseDto = userEntity.getCourses().stream()
                    .map(course -> modelMapper.map(course, CourseResponseDto.class)).toList();

            userResponseDto.setCourses(courseResponseDto);
        }
        return userResponseDto;

    }

}
