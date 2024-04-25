package com.grupo2.aulavirtual.Repository;



import com.grupo2.aulavirtual.Entities.UserEntity;
import com.grupo2.aulavirtual.Enum.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo2.aulavirtual.Entities.RoleEntity;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
    Optional<RoleEntity> findByRole(RoleEnum role);

    Boolean existsByRole(RoleEnum role);
}
