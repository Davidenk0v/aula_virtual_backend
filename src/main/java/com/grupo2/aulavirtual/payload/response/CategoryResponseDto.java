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

    private String category;

    private List<CourseResponseDto> course;
}
