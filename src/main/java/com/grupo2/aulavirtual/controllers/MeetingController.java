package com.grupo2.aulavirtual.controllers;


import com.grupo2.aulavirtual.entities.MeetingEntity;
import com.grupo2.aulavirtual.payload.request.SubjectDTO;
import com.grupo2.aulavirtual.services.impl.MeetingServiceImpl;
import com.grupo2.aulavirtual.services.impl.SubjectsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/meetings")
public class MeetingController {

    @Autowired
    private MeetingServiceImpl meetingService;

    @GetMapping("/")
    public ResponseEntity<?> getAllSubjectsDTO() {
        return meetingService.repositoryMeeting();
    }

    @PostMapping("/")
    public ResponseEntity<?> saveSubject( @RequestBody MeetingEntity meeting) {
        return meetingService.postMeeting(meeting);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
        return  meetingService.deleteMeeting(id);
    }

}
