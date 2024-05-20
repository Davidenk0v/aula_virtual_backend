package com.grupo2.aulavirtual.repositories;

import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.UserImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<UserImg, Long> {
}
