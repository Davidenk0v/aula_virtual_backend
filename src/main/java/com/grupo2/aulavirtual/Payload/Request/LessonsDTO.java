package com.grupo2.aulavirtual.Payload.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonsDTO {

    private Long idLesson;

    private String name;

    private String contenido;

    private String description;

    private SubjectDTO subject;
}
