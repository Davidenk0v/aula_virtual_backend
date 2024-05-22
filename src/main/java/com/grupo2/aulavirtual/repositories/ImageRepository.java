package com.grupo2.aulavirtual.repositories;


import com.grupo2.aulavirtual.entities.UserImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<UserImg, Long> {

    Optional<UserImg> findByIdUser(String idUser);
}
