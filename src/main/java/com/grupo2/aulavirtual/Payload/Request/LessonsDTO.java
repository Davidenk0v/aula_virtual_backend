package com.grupo2.aulavirtual.Payload.Request;

import com.grupo2.aulavirtual.Entities.SubjectsEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
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

    private SubjectsEntity subject;
}
