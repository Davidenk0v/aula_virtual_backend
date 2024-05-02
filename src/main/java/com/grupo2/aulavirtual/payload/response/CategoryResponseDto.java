package com.grupo2.aulavirtual.payload.response;

import java.util.List;

import com.grupo2.aulavirtual.entities.enums.CategoryEnum;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryResponseDto {

    private Long idCategory;

    private CategoryEnum category;

    private List<CourseResponseDto> course;
}
