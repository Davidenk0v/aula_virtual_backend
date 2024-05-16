package com.grupo2.aulavirtual.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;


@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
public class LessonsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLesson;

    @Basic
    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String contenido;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "idSubject")
    @JsonIgnore
    private SubjectsEntity subject;
}
