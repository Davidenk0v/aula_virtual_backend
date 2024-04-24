package com.grupo2.aulavirtual.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo2.aulavirtual.Entities.AdressEntity;

@Repository
public interface AddressRepository extends JpaRepository<AdressEntity, Long>{
    
}
