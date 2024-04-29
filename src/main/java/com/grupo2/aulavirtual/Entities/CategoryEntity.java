package com.grupo2.aulavirtual.Entities;

import java.util.List;

import com.grupo2.aulavirtual.Enum.CategoryEnum;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategory;

    @Basic
    @Enumerated(EnumType.STRING) 
    CategoryEnum category;

    @ManyToMany(
    mappedBy = "category"
    )
   private List<CourseEntity> course;
}
