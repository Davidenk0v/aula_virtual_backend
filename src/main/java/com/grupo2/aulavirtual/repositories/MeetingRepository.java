package com.grupo2.aulavirtual.repositories;

import com.grupo2.aulavirtual.entities.MeetingEntity;
import com.grupo2.aulavirtual.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<MeetingEntity, Long> {


    void deleteByNumberMeeting(Long numberMeeting);
}


