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


    private static final String NOT_FOUND = "No encontrado";
    private static final String SAVE = "data";
    private static final String ERROR = "error";
    @Override
    public ResponseEntity<?> repositoryMeeting() {
        List<MeetingEntity> meetingEntities = repositoryMeeting.findAll();
        if(meetingEntities.isEmpty()){
            return new ResponseEntity<>(NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(meetingEntities, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HashMap<String, ?>> postMeeting( MeetingEntity meeting) {
        HashMap<String, MeetingEntity> response = new HashMap<>();
        MeetingEntity responseEntity = repositoryMeeting.save(meeting);
        response.put(SAVE, responseEntity);
        return ResponseEntity.status(201).body(response);

    }


    @Override
    public ResponseEntity<HashMap<String, ?>> deleteMeeting(Long id) {
        HashMap<String, Long> response = new HashMap<>();
        if (repositoryMeeting.existsById(id)){
            repositoryMeeting.deleteByNumberMeeting(id);
            response.put(SAVE, id);
        }else {
            response.put(NOT_FOUND, id);
        }
        return ResponseEntity.status(201).body(response);

    }








}

