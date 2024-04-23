package com.grupo2.aulavirtual.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LessonsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLesson;

    @Basic
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contenido;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(
    		name = "idSubject")
    @JsonIgnore
    private SubjectsEntity subject;
}
