package com.grupo2.aulavirtual.repositories;



import com.grupo2.aulavirtual.entities.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo2.aulavirtual.entities.RoleEntity;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
    Optional<RoleEntity> findByRole(RoleEnum role);

    Boolean existsByRole(RoleEnum role);
}
