package com.grupo2.aulavirtual.Payload.Response;

import java.util.List;

import com.grupo2.aulavirtual.Enum.CategoryEnum;

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
