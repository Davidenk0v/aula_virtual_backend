package com.grupo2.aulavirtual.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.UserEntity;
import lombok.*;

import java.sql.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentResponseDto {

    private int idComment;

    private String text;

    private Date date;

    
    private UserResponseDto user;

    @JsonIgnore
    private CourseResponseDto course;

}
