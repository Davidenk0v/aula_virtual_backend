package com.grupo2.aulavirtual.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meeting", uniqueConstraints = { @UniqueConstraint(columnNames = { "numberMeeting" }) })
public class MeetingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMeeting;


    @Basic
    @Column(nullable = false)
    private String nameTeacher;

    @Basic
    @Column(nullable = false)
    private long numberMeeting;

    @Basic
    @Column(nullable = false)
    private String password;

    //@ManyToOne
    //@JoinColumn(
    //        name = "idCourse")
    //@JsonIgnore
    //private CourseEntity course;
}
