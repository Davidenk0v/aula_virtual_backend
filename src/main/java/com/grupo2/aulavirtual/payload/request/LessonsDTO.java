package com.grupo2.aulavirtual.payload.request;

import com.grupo2.aulavirtual.entities.SubjectsEntity;

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
