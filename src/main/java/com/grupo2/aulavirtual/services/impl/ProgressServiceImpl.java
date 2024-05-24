package com.grupo2.aulavirtual.services.impl;

import com.grupo2.aulavirtual.entities.ProgressEntity;
import com.grupo2.aulavirtual.repositories.ProgressRepository;
import com.grupo2.aulavirtual.services.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ProgressServiceImpl implements ProgressService {

    @Autowired
    ProgressRepository repository;


    private static final String SAVE = "data";
    private static final String ERROR = "error";


    @Override
    public ResponseEntity<?> postProgress(String idKeyCloak, Long idCourse){
        try {
            HashMap<String,ProgressEntity> response = new HashMap<>();
            ProgressEntity post = new ProgressEntity();
            post.setIdKeyCloak(idKeyCloak);
            post.setIdCourse(idCourse);
            post.setProgress(0);
            post = repository.save(post);
            response.put(SAVE,post);
            return ResponseEntity.status(201).body(response);
        }catch (Exception e){
            HashMap<String,String> responseError = new HashMap<>();
            responseError.put(ERROR,e.getMessage());
            return ResponseEntity.status(500).body(responseError);
        }
    }
    @Override
    public ResponseEntity<?> sumValue(String idKeyCloak,Long idCourse,float progress){
        try {
            HashMap<String,ProgressEntity> response = new HashMap<>();
            ProgressEntity post = repository.findByIdCourseAndIdKeyCloak(idCourse,idKeyCloak).get();
            post.setProgress(post.getProgress()+progress);
            post = repository.save(post);
            response.put(SAVE,post);
            return ResponseEntity.status(201).body(response);
        }catch (Exception e){
            HashMap<String,String> responseError = new HashMap<>();
            responseError.put(ERROR,e.getMessage());
            return ResponseEntity.status(500).body(responseError);
        }
    }

    @Override
    public ResponseEntity<?> getProgress(String idKeyCloak,Long idCourse){
        try {
            HashMap<String,ProgressEntity> response = new HashMap<>();
            ProgressEntity post = repository.findByIdCourseAndIdKeyCloak(idCourse,idKeyCloak).get();
            response.put(SAVE,post);
            return ResponseEntity.status(200).body(response);
        }catch (Exception e){
            HashMap<String,String> responseError = new HashMap<>();
            responseError.put(ERROR,e.getMessage());
            return ResponseEntity.status(500).body(responseError);
        }
    }
    @Override
    public ResponseEntity<?> deleteProgress(String idKeyCloak,Long idCourse){
        try {
            HashMap<String,String > response = new HashMap<>();
            ProgressEntity post = repository.findByIdCourseAndIdKeyCloak(idCourse,idKeyCloak).get();
            repository.delete(post);
            response.put(SAVE,"Borrado id: "+post.getId());
            return ResponseEntity.status(201).body(response);
        }catch (Exception e){
            HashMap<String,String> responseError = new HashMap<>();
            responseError.put(ERROR,e.getMessage());
            return ResponseEntity.status(500).body(responseError);
        }
    }
}