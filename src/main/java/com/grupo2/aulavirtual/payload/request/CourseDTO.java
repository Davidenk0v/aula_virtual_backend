package com.grupo2.aulavirtual.payload.request;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

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

    private String idTeacher;

    private List<String> users;

    private String urlImg;
}
