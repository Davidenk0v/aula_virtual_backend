package com.grupo2.aulavirtual.Payload.Request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectDTO {
    private Long idSubject;

    private String name;

    private String description;

    private CourseDTO course;

    private List<LessonsDTO> lessons;
}
