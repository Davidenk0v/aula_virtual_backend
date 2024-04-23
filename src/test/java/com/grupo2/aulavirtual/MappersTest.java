package com.grupo2.aulavirtual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import com.grupo2.aulavirtual.Entities.LessonsEntity;
import com.grupo2.aulavirtual.Entities.SubjectsEntity;
import com.grupo2.aulavirtual.Payload.Request.LessonsDTO;

public class MappersTest {

    private ModelMapper mapper;

    @BeforeEach
    public void setup() {
        this.mapper = new ModelMapper();
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
    public void whenMapGameWithDeepMapping_thenConvertsToDTO() {
        // setup
        TypeMap<LessonsEntity, LessonsDTO> propertyMapper = this.mapper.createTypeMap(LessonsEntity.class,
                LessonsDTO.class);
        // add deep mapping to flatten source's Player object into a single field in
        // destination
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getSubject().getName(), LessonsDTO::setSubject));

        // when map between different hierarchies
        LessonsEntity lessonsEntity = new LessonsEntity(1L, "Game 1", "Game 1 description", "Game 1 content", null);
        lessonsEntity.setSubject(new SubjectsEntity(1L, "John", null, null, null));
        LessonsDTO lessonsDTO = this.mapper.map(lessonsEntity, LessonsDTO.class);

        // then
        assertEquals(lessonsEntity.getSubject().getName(), lessonsDTO.getSubject());
    }
}
