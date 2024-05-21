package com.grupo2.aulavirtual.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProgressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(nullable = false)
    private String idKeyCloak;

    @Basic
    @Column(nullable = false)
    private Long idCourse;


    @Basic
    @Column(nullable = false)
    private float progress;
}
