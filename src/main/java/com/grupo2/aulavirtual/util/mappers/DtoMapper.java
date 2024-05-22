package com.grupo2.aulavirtual.util.mappers;

import java.util.List;

import com.grupo2.aulavirtual.payload.request.*;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;

import com.grupo2.aulavirtual.entities.CategoryEntity;
import com.grupo2.aulavirtual.entities.CommentEntity;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.LessonsEntity;
import com.grupo2.aulavirtual.entities.SubjectsEntity;
import com.grupo2.aulavirtual.payload.response.CategoryResponseDto;
import com.grupo2.aulavirtual.payload.response.CommentResponseDto;
import com.grupo2.aulavirtual.payload.response.CourseResponseDto;
import com.grupo2.aulavirtual.payload.response.LessonsResponseDto;
import com.grupo2.aulavirtual.payload.response.SubjectsResponseDto;
import com.grupo2.aulavirtual.payload.response.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    ModelMapper modelMapper = new ModelMapper();

    public UserDTO userRepresentationToDto (UserRepresentation userRepresentation) {
        return UserDTO.builder()
                .idUser(userRepresentation.getId())
                .email(userRepresentation.getEmail())
                .password(userRepresentation.getCredentials().get(0).getValue())
                .firstname(userRepresentation.getFirstName())
                .lastname(userRepresentation.getLastName())
                .build();
    }

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

    public CommentEntity dtoToEntity(CommentDTO CommentDTO) {
        return modelMapper.map(CommentDTO, CommentEntity.class);
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
                .price(courseEntity.getPrice())
                .urlImg(courseEntity.getUrlImg())
                .build();

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


    public CommentResponseDto entityToResponseDto(CommentEntity commentEntity) {

        CommentResponseDto commentResponseDto = new CommentResponseDto()
                .builder()
                .idComment(commentEntity.getIdComment())
                .text(commentEntity.getText())
                .date(commentEntity.getDate())
                .build();

        if (commentEntity.getUserId() != null) {
            UserResponseDto userResponseDto = modelMapper.map(commentEntity.getUserId(), UserResponseDto.class);
            commentResponseDto.setUser(userResponseDto);
        }

        if (commentEntity.getCourse() != null) {
            CourseResponseDto courseResponseDto = modelMapper.map(commentEntity.getCourse(), CourseResponseDto.class);
            commentResponseDto.setCourse(courseResponseDto);
        }
        return commentResponseDto;

    }

}
