package com.grupo2.aulavirtual.Controllers;

import com.grupo2.aulavirtual.Payload.Request.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subject")
public class SubjectController {


        @Autowired
        private SubjectService subjectService;

        @GetMapping("/")
        public ResponseEntity<?> getAllSubjectsDTO() {

            return null;
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> getSubjectById(@PathVariable Long id) {

            return null;
        }

    @PostMapping("/")
    public ResponseEntity<?> saveSubject(@RequestBody SubjectDTO Subject) {

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubject(@RequestBody SubjectDTO Subject, @PathVariable Long id) {
        return null;
    }
        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteSubjectById(@PathVariable Long id) {

            return null;
        }

    }
}
