package com.grupo2.aulavirtual.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo2.aulavirtual.entities.CourseEntity;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long>{
    
}
