package com.grupo2.aulavirtual.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo2.aulavirtual.entities.LessonsEntity;

@Repository
public interface LessonsRepository extends JpaRepository<LessonsEntity, Long>{
    
}
