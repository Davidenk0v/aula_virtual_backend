package com.grupo2.aulavirtual.Payload.Request;

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
public class LessonsDTO {

    private Long idLesson;

    private String name;

    private String contenido;

    private String description;

    private SubjectDTO subject;
}
