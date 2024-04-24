package com.grupo2.aulavirtual.Payload.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectsResponseDto {

    private Long idSubject;

    private String name;

    private String description;

    private CourseResponseDto course;

    private List<LessonsResponseDto> lessons;
}
