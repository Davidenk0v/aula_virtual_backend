package com.grupo2.aulavirtual.Payload.Request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private Long idSubject;

    private String name;

    private String description;

    private CourseDTO course;

    private List<LessonsDTO> lessons;
}
