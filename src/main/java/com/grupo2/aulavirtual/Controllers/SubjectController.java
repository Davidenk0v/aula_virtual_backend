package com.grupo2.aulavirtual.Controllers;

import com.grupo2.aulavirtual.Payload.Request.SubjectDTO;
import com.grupo2.aulavirtual.Services.SubjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subject")
public class SubjectController {

     @Autowired
     private SubjectsService subjectService;

    @GetMapping("/")
    public ResponseEntity<?> getAllSubjectsDTO() {
        return subjectService.subjectsList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable Long id) {
        return subjectService.findSubjectById(id);
    }

    @PostMapping("/{idCourse}")
    public ResponseEntity<?> saveSubject(@PathVariable Long idCourse,@RequestBody SubjectDTO Subject) {
        return subjectService.postSubject(idCourse, Subject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubject(@RequestBody SubjectDTO subjectDTO, @PathVariable Long id) {
        return subjectService.updateSubject(id,subjectDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubjectById(@PathVariable Long id) {
        return subjectService.deleteSubject(id);
    }

}
