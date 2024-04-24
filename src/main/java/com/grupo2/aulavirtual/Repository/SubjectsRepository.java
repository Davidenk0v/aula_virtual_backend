package com.grupo2.aulavirtual.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo2.aulavirtual.Entities.SubjectsEntity;

@Repository
public interface SubjectsRepository extends JpaRepository<SubjectsEntity, Long>{
    
}
