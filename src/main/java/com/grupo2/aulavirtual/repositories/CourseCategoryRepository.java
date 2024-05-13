package com.grupo2.aulavirtual.repositories;

import com.grupo2.aulavirtual.entities.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CourseCategoryRepository {

        public List<CourseEntity> filterCoursesByCategories(List<String> categories) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
            EntityManager em = emf.createEntityManager();

            // 1. Build JPQL Query (using category names)
            String query = "SELECT c FROM Course c JOIN c.categories cat " +
                    "WHERE cat.name IN (:categoryNames)";

            // 2. Prepare distinct category names (case-insensitive)
            List<String> distinctCategoryNames = new ArrayList<>(categories);
            distinctCategoryNames = distinctCategoryNames.stream()
                    .map(String::toLowerCase)
                    .distinct()
                    .collect(Collectors.toList());

            // 3. Create Query Object
            Query q = em.createQuery(query);

            // 4. Set dynamic parameter (case-insensitive)
            q.setParameter("categories", distinctCategoryNames);

            // 5. Execute Query and Return Results
            List<CourseEntity> filteredCourses = q.getResultList();
            return filteredCourses;
        }
}
