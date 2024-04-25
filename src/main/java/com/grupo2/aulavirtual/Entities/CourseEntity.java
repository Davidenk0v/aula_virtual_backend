package com.grupo2.aulavirtual.Entities;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;

/**
 * Course
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCourse;
    @Basic
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date finishDate;

    @Column(nullable = false)
    private BigDecimal pago;


    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<SubjectsEntity> subjects;

    @ManyToMany
    @JoinTable(name = "course_category", joinColumns = @JoinColumn(name = "idCourse", referencedColumnName = "idCourse"), inverseJoinColumns = @JoinColumn(name = "idCategory", referencedColumnName = "idCategory"))
    private List<CategoryEntity> category;

    @ManyToMany(mappedBy = "courses")
    private List<UserEntity> user;

}