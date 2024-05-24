package com.grupo2.aulavirtual.repositories;

import com.grupo2.aulavirtual.entities.LessonsEntity;
import com.grupo2.aulavirtual.entities.ProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<ProgressEntity, Long> {
    @Query("SELECT p.progress FROM ProgressEntity p WHERE p.idKeyCloak = :idKeyCloak AND p.idCourse = :idCourse")
    Optional<Float> findProgressByIdKeyCloakAndIdCourse(@Param("idKeyCloak") String idKeyCloak, @Param("idCourse") Long idCourse);

    Optional<ProgressEntity> findByIdCourseAndIdKeyCloak(Long idCourse, String idKeyCloak);
}
