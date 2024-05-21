package com.grupo2.aulavirtual.repositories;

import com.grupo2.aulavirtual.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grupo2.aulavirtual.entities.CourseEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    @Query("SELECT c FROM CourseEntity c WHERE LOWER(c.name) LIKE %:keyword%")
    Optional<List<CourseEntity>> findByKeyword(@Param("keyword") String keyword);

    Set<CourseEntity> findCoursesByCategory(CategoryEntity category);

    @Query("SELECT c FROM CourseEntity c WHERE :userId MEMBER OF c.usersId")
    Set<CourseEntity> findCoursesByUsersId(@Param("userId") String userId);

    Set<CourseEntity> findCoursesByIdTeacher (String idTeacher);


}
