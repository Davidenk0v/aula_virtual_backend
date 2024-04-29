package com.grupo2.aulavirtual.Payload.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LessonsResponseDto {

    private Long idLesson;

    private String name;

    private String contenido;

    private String description;

    @JsonIgnore
    private SubjectsResponseDto subject;
}
