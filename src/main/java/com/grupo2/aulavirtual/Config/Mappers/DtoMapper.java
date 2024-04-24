package com.grupo2.aulavirtual.Config.Mappers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import com.grupo2.aulavirtual.Entities.AdressEntity;
import com.grupo2.aulavirtual.Entities.CategoryEntity;
import com.grupo2.aulavirtual.Entities.CourseEntity;
import com.grupo2.aulavirtual.Entities.LessonsEntity;
import com.grupo2.aulavirtual.Entities.RoleEntity;
import com.grupo2.aulavirtual.Entities.SubjectsEntity;
import com.grupo2.aulavirtual.Entities.UserEntity;
import com.grupo2.aulavirtual.Payload.Request.AddressDTO;
import com.grupo2.aulavirtual.Payload.Request.CategoryDTO;
import com.grupo2.aulavirtual.Payload.Request.CourseDTO;
import com.grupo2.aulavirtual.Payload.Request.LessonsDTO;
import com.grupo2.aulavirtual.Payload.Request.RoleDTO;
import com.grupo2.aulavirtual.Payload.Request.UserDTO;
import com.grupo2.aulavirtual.Payload.Response.AddressResponseDto;
import com.grupo2.aulavirtual.Payload.Response.CategoryResponseDto;
import com.grupo2.aulavirtual.Payload.Response.CourseResponseDto;
import com.grupo2.aulavirtual.Payload.Response.LessonsResponseDto;
import com.grupo2.aulavirtual.Payload.Response.RoleResponseDto;
import com.grupo2.aulavirtual.Payload.Response.SubjectsResponseDto;
import com.grupo2.aulavirtual.Payload.Response.UserResponseDto;

public class DtoMapper {

    ModelMapper modelMapper = new ModelMapper();

    public AdressEntity dtoToEntity(AddressDTO addressDTO) {
        return modelMapper.map(addressDTO, AdressEntity.class);
    }

    public CategoryEntity dtoToEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, CategoryEntity.class);
    }

    public CourseEntity dtoToEntity(CourseDTO courseDTO) {
        return modelMapper.map(courseDTO, CourseEntity.class);
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

    public AddressResponseDto entityToResponse(AdressEntity adressEntity) {

        if (adressEntity.getUser() != null) {
            UserResponseDto userResponseDto = modelMapper.map(adressEntity.getUser(), UserResponseDto.class);
            return new AddressResponseDto().builder()
                    .idAdress(adressEntity.getIdAdress())
                    .country(adressEntity.getCountry())
                    .number(adressEntity.getNumber())
                    .street(adressEntity.getStreet())
                    .city(adressEntity.getCity())
                    .postalCode(adressEntity.getPostalCode())
                    .user(userResponseDto)
                    .build();
        } else {
            return new AddressResponseDto().builder()
                    .idAdress(adressEntity.getIdAdress())
                    .country(adressEntity.getCountry())
                    .number(adressEntity.getNumber())
                    .street(adressEntity.getStreet())
                    .city(adressEntity.getCity())
                    .postalCode(adressEntity.getPostalCode())
                    .build();
        }

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
                .pago(courseEntity.getPago())
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
                .role(userEntity.getRole().getRole().name())
                .build();

        if (userEntity.getAddress() != null) {
            AddressResponseDto addressResponseDto = modelMapper.map(userEntity.getAddress(), AddressResponseDto.class);
            userResponseDto.setAddress(addressResponseDto);
        }

        if (userEntity.getCourses() != null) {
            List<CourseResponseDto> courseResponseDto = userEntity.getCourses().stream()
                    .map(course -> modelMapper.map(course, CourseResponseDto.class)).toList();

            userResponseDto.setCourses(courseResponseDto);
        }
        return userResponseDto;

    }

}
