package com.grupo2.aulavirtual.payload.request;

import java.util.List;

import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.enums.CategoryEnum;

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
public class CategoryDTO {

    private Long idCategory;

    private CategoryEnum category;

    private List<CourseEntity> course;
}
