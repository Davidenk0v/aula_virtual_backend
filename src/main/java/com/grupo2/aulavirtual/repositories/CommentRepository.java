package com.grupo2.aulavirtual.repositories;

import com.grupo2.aulavirtual.entities.CommentEntity;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    @Query("SELECT c FROM CommentEntity c JOIN c.course cu WHERE cu.idCourse= :idCourse")
    List<CommentEntity> findCommentsByIdCourse(@Param("idCourse") long idCourse); 
}
