package com.grupo2.aulavirtual.Controllers;

import java.util.List;

import com.grupo2.aulavirtual.Entities.SubjectsEntity;
import com.grupo2.aulavirtual.Payload.Request.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/{id}")
    public List<subjectDTO> getSubjectsDTO() {

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long id) {

        return null;
    }

    @PostMapping("/{id}")
    public SubjectEntity saveSubject(@RequestBody SubjectEntity subject) {

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubjectById(@PathVariable Long id) {

        return null;
    }

}