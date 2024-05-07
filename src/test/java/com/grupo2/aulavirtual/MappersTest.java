package com.grupo2.aulavirtual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.grupo2.aulavirtual.mappers.DtoMapper;
import com.grupo2.aulavirtual.entities.LessonsEntity;
import com.grupo2.aulavirtual.payload.request.LessonsDTO;

public class MappersTest {

    private ModelMapper mapper;

    private DtoMapper dtoMapper;

    @BeforeEach
    public void setup() {
        this.mapper = new ModelMapper();
        this.dtoMapper = new DtoMapper();
    }

    @Test
    public void whenMapGameWithExactMatch_thenConvertsToDTO() {
        // when similar source object is provided
        LessonsEntity lesson = new LessonsEntity(1L, "Game 1", "Game 1 description", "Game 1 content", null);
        LessonsDTO lessonsDTO = this.mapper.map(lesson, LessonsDTO.class);

        // then it maps by default
        assertEquals(lesson.getIdLesson(), lessonsDTO.getIdLesson());
        assertEquals(lesson.getName(), lessonsDTO.getName());
        System.out.println(lessonsDTO.toString());
    }

    @Test
    public void createResponseDto() {
        AdressEntity adressEntity = new AdressEntity().builder().idAdress(1L).country("Argentina").number("123")
                .street("Street 1").city("City 1")
                .postalCode("1234").build();
        // given
        AddressResponseDto addressResponseDto = dtoMapper.entityToResponse(adressEntity);

        assertEquals(adressEntity.getIdAdress(), addressResponseDto.getIdAdress());
        assertEquals(adressEntity.getCountry(), addressResponseDto.getCountry());
    }

}
