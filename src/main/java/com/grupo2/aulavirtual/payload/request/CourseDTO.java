package com.grupo2.aulavirtual.payload.request;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.grupo2.aulavirtual.entities.UserEntity;

import lombok.*;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseDTO {

    private Long idCourse;

    private String name;

    private String description;

    private Date startDate;

    private Date finishDate;

    private BigDecimal price;

    private Long idTeacher;

    private List<UserEntity> user;

    private String urlImg;
}
