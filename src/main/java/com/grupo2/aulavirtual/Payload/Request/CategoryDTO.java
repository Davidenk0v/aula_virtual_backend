package com.grupo2.aulavirtual.Payload.Request;

import java.util.List;

import com.grupo2.aulavirtual.Enum.CategoryEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long idCategory;

    private CategoryEnum category;

    private List<CourseDTO> course;
}
