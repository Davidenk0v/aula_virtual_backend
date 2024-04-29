package com.grupo2.aulavirtual.Payload.Response;

import java.util.List;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectsResponseDto {

    private Long idSubject;

    private String name;

    private String description;

    private CourseResponseDto course;

    private List<LessonsResponseDto> lessons;
}
