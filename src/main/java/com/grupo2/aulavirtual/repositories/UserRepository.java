package com.grupo2.aulavirtual.repositories;



import com.grupo2.aulavirtual.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo2.aulavirtual.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByIdKeycloak(String idKeycloak);
    Optional<UserEntity> findByUsername(String username);
    Optional<List<CourseEntity>> findCoursesByIdUser(Long userId);



}
