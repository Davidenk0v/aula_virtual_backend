package com.grupo2.aulavirtual.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo2.aulavirtual.entities.CourseEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long>{

    Optional<List<CourseEntity>> findAllByNameContains(String name);
}
