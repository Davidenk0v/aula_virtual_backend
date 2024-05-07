package com.grupo2.aulavirtual.repositories;



import com.grupo2.aulavirtual.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo2.aulavirtual.entities.SubjectsEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectsRepository extends JpaRepository<SubjectsEntity, Long>{
    Optional<List<SubjectsEntity>> findByCourseIdCourse(Long userId);
}
