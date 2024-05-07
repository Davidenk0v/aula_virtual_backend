package com.grupo2.aulavirtual.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grupo2.aulavirtual.entities.CourseEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long>{



    @Query("SELECT c FROM CourseEntity c WHERE LOWER(c.name) LIKE %:keyword%")
    Optional<List<CourseEntity>> findByKeyword(@Param("keyword") String keyword);
}
