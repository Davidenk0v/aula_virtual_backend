package com.grupo2.aulavirtual.controllers;

import com.grupo2.aulavirtual.payload.request.SubjectDTO;
import com.grupo2.aulavirtual.services.impl.SubjectsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    @Autowired
    private SubjectsServiceImpl subjectService;

    @GetMapping("/")
    public ResponseEntity<?> getAllSubjectsDTO() {
        return subjectService.subjectsList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable Long id) {
        return subjectService.findSubjectById(id);
    }

    @PostMapping("/{idCourse}")
    public ResponseEntity<?> saveSubject(@PathVariable Long idCourse, @RequestBody SubjectDTO subjectDTO) {
        return subjectService.postSubject(idCourse, subjectDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubject(@RequestBody SubjectDTO subjectDTO, @PathVariable Long id) {
        return subjectService.updateSubject(id, subjectDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubjectById(@PathVariable Long id) {
        return subjectService.deleteSubject(id);
    }

    @GetMapping("/lista/{idCourse}")
    public ResponseEntity<?> listByCourseId(@PathVariable Long idCourse) {
        return subjectService.subjectsListByIdCourse(idCourse);
    }

}
