package com.grupo2.aulavirtual.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo2.aulavirtual.Entities.LessonsEntity;

@Repository
public interface LessonsRepository extends JpaRepository<LessonsEntity, Long>{
    
}
