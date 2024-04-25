package com.grupo2.aulavirtual.Payload.Response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {

    private Long idCourse;

    private String name;

    private String description;

    private Date startDate;

    private Date finishDate;

    private BigDecimal pago;

    @JsonIgnore
    private List<UserResponseDto> users;
}
