package com.grupo2.aulavirtual.payload.request;

import com.grupo2.aulavirtual.entities.CourseEntity;
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

    private String userId;

    private CourseEntity course;

}
