package com.grupo2.aulavirtual.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo2.aulavirtual.entities.AdressEntity;

@Repository
public interface AddressRepository extends JpaRepository<AdressEntity, Long>{
    
}
