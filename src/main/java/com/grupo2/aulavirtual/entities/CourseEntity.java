package com.grupo2.aulavirtual.entities;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Course
 */
@ToString
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

    private BigDecimal price;

    private String urlImg;

    @CreatedDate
    @Column(insertable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false, nullable = true)
    private LocalDateTime lastModifiedDate;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SubjectsEntity> subjects;

    @ManyToMany
    @JoinTable(name = "course_category", joinColumns = @JoinColumn(name = "idCourse", referencedColumnName = "idCourse"), inverseJoinColumns = @JoinColumn(name = "idCategory", referencedColumnName = "idCategory"))
    private List<CategoryEntity> category;

    @ManyToMany(mappedBy = "courses")
    private List<UserEntity> user;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private List<CommentEntity> comments;

    public CourseEntity(long l, String name, String description, java.util.Date date, java.util.Date date1, BigDecimal price, String url, CategoryEntity category) {
        this.idCourse = l;
        this.name = name;
        this.description = description;
        this.startDate = new Date(date.getTime());
        this.finishDate = new Date(date1.getTime());
        this.price = price;
        this.urlImg = url;
        this.category = List.of(category);

    }
}