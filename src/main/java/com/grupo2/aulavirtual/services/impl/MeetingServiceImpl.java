package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.entities.MeetingEntity;
import com.grupo2.aulavirtual.repositories.CourseRepository;
import com.grupo2.aulavirtual.repositories.MeetingRepository;
import com.grupo2.aulavirtual.services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    CourseRepository repository;


    @Autowired
    MeetingRepository repositoryMeeting;



    @Override
    public ResponseEntity<?> repositoryMeeting() {
        List<MeetingEntity> meetingEntities = repositoryMeeting.findAll();
        if(meetingEntities.isEmpty()){
            return new ResponseEntity<>("No se encontraron temas", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(meetingEntities, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HashMap<String, ?>> postMeeting( MeetingEntity meeting) {
        HashMap<String, MeetingEntity> response = new HashMap<>();
        MeetingEntity responseEntity = repositoryMeeting.save(meeting);
        response.put("Subido", responseEntity);
        return ResponseEntity.status(201).body(response);

    }


    @Override
    public ResponseEntity<HashMap<String, ?>> deleteMeeting(Long id) {
        HashMap<String, Long> response = new HashMap<>();
        if (repositoryMeeting.existsById(id)){
            repositoryMeeting.deleteByNumberMeeting(id);
            response.put("Borrado", id);
        }else {
            response.put("No encontrado", id);
        }
        return ResponseEntity.status(201).body(response);

    }








}

