package com.grupo2.aulavirtual.payload.request;

import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.UserEntity;
import lombok.*;

import java.sql.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDTO {

    private int idComment;

    private String text;

    private Date date;

    private UserEntity user;

    private CourseEntity course;

}
