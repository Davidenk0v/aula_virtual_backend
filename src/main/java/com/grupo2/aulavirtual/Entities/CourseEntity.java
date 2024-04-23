package com.grupo2.aulavirtual.Entities;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Course
 */
@Data
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
    private String  name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date finishDate;

    @Column(nullable = false)
    private double pago;
    
   @ManyToMany(
    mappedBy = "course"
   )
   private List<UserEntity> user;
    
   @ManyToMany
   @JoinTable(
    name = "course_category",
    joinColumns = @JoinColumn(
        name = "idCourse",
        referencedColumnName = "idCourse"),
    inverseJoinColumns = @JoinColumn(
        name = "idCategory",
        referencedColumnName = "idCategory"))
   private List<CategoryEntity> category;
   

   @OneToMany(
            mappedBy = "course",
    		cascade = CascadeType.ALL,
    		fetch = FetchType.EAGER)
    private List<SubjectsEntity> subjects;
}