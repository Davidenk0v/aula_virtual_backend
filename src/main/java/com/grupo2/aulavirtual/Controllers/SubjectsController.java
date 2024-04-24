package com.grupo2.aulavirtual.Controllers;

import java.util.List;

import com.grupo2.aulavirtual.Entities.SubjectsEntity;
import com.grupo2.aulavirtual.Payload.Request.SubjectDTO;


@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public List<SubjectDTO> getSubjectDTO() {

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long id) {

        return null;
    }

    @PostMapping
    public SubjectsEntity saveSubject(@RequestBody SubjectsEntity Subject) {

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubjectById(@PathVariable Long id) {

        return null;
    }

