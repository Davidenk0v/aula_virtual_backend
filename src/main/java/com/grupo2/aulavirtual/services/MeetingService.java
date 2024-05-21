package com.grupo2.aulavirtual.services;

import com.grupo2.aulavirtual.entities.MeetingEntity;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface MeetingService {

    ResponseEntity<?> repositoryMeeting();

    ResponseEntity<HashMap<String, ?>> postMeeting(MeetingEntity meeting);

    ResponseEntity<HashMap<String, ?>> deleteMeeting(Long id);
}
