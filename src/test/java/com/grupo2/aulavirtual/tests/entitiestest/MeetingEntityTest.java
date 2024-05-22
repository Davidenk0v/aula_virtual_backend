package com.grupo2.aulavirtual.tests.entitiestest;

import com.grupo2.aulavirtual.entities.MeetingEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MeetingEntityTest {

    @Test
    void testGettersAndSetters() {
        MeetingEntity meetingEntity = MeetingEntity.builder()
                .idMeeting(1L)
                .nameTeacher("Joao")
                .numberMeeting(123456789L)
                .password("1234")
                .build();

        assertEquals(1L, meetingEntity.getIdMeeting(), "idMeeting getter must be equal to 1L");
        meetingEntity.setIdMeeting(2L);
        assertEquals(2L, meetingEntity.getIdMeeting(), "idMeeting setter must be equal to 2L");

        assertEquals("Joao", meetingEntity.getNameTeacher(), "nameTeacher getter must be equal to 'Joao'");
        meetingEntity.setNameTeacher("Maria");
        assertEquals("Maria", meetingEntity.getNameTeacher(), "nameTeacher setter must be equal to 'Maria'");

        assertEquals(123456789L, meetingEntity.getNumberMeeting(), "numberMeeting getter must be equal to 123456789L");
        meetingEntity.setNumberMeeting(987654321L);
        assertEquals(987654321L, meetingEntity.getNumberMeeting(), "numberMeeting setter must be equal to 987654321L");

        assertEquals("1234", meetingEntity.getPassword(), "password getter must be equal to '1234'");
        meetingEntity.setPassword("abcd");
        assertEquals("abcd", meetingEntity.getPassword(), "password setter must be equal to 'abcd'");
    }

    @Test
    void testNotNull() {
        MeetingEntity meetingEntity = new MeetingEntity();
        assertNotNull(meetingEntity);
    }
}
