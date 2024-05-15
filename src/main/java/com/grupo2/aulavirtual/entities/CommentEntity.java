package com.grupo2.aulavirtual.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "commentEntity")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idComment;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private Date date;

    @ManyToOne(
            fetch = FetchType.EAGER)
    @JoinColumn(
            name = "idUser", referencedColumnName = "idUser")
    private UserEntity user;
    @ManyToOne(
            fetch = FetchType.EAGER)
    @JoinColumn(
            name = "idCourse", referencedColumnName = "idCourse")
    private CourseEntity course;

}
