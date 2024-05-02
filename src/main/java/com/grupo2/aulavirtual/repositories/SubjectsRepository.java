package com.grupo2.aulavirtual.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo2.aulavirtual.entities.SubjectsEntity;

@Repository
public interface SubjectsRepository extends JpaRepository<SubjectsEntity, Long>{
    
}
