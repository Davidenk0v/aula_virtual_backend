package com.grupo2.aulavirtual.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo2.aulavirtual.entities.CategoryEntity;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String>{

    Optional<CategoryEntity> findByCategoryContaining(String categoryName);
}
