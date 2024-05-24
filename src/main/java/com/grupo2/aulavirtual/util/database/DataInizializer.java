package com.grupo2.aulavirtual.util.database;

import com.grupo2.aulavirtual.entities.CategoryEntity;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.repositories.CategoryRepository;
import com.grupo2.aulavirtual.repositories.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class DataInizializer implements CommandLineRunner {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CourseRepository courseRepository;

    Logger logger = LoggerFactory.getLogger(DataInizializer.class);


    @Override
    public void run(String... args) throws Exception {
        try {
            CategoryEntity category1 = (new CategoryEntity(1L, "Programming", null));
            CategoryEntity category2 = (new CategoryEntity(2L, "Web Development", null));
            CategoryEntity category3 = (new CategoryEntity(3L, "Data Science", null));
            CategoryEntity category4 = (new CategoryEntity(4L, "Design", null));
            CategoryEntity category5 = (new CategoryEntity(5L, "Business", null));
            CategoryEntity category6 = (new CategoryEntity(6L, "Marketing", null));
            CategoryEntity category7 = (new CategoryEntity(7L, "Languages", null));
            CategoryEntity category8 = (new CategoryEntity(8L, "Music", null));
            CategoryEntity category9 = (new CategoryEntity(9L, "Health and Fitness", null));
            if (categoryRepository.count() == 0) {
                // Insertar categorías

                categoryRepository.saveAll(List.of(category1, category2, category3, category4, category5, category6, category7, category8, category9));

                logger.info("Base de datos inicializada con categorías.");
            } else {
                // Si la base de datos ya tiene datos, no se hace nada
                logger.info("La tabla de categorias ya está inicializada.");
            }

            if (courseRepository.count() == 0) {
                // Insertar cursos
                CourseEntity course1 = new CourseEntity(1L, "Java Programming", "Introduction to Java programming", new Date(), new Date(), new BigDecimal("199.99"), "def-course.jpg", category1);
                CourseEntity course2 = new CourseEntity(2L,"Web Development Basics", "Fundamentals of web development", new Date(), new Date(), new BigDecimal("149.99"), "def-course.jpg", category1);
                CourseEntity course3 = new CourseEntity(3L,"Data Analysis with Python", "Using Python for data analysis", new Date(), new Date(), new BigDecimal("249.99"), "def-course.jpg", category2);
                CourseEntity course4 = new CourseEntity(4L,"Machine Learning Fundamentals", "Introduction to machine learning", new Date(), new Date(), new BigDecimal("299.99"), "def-course.jpg", category3);
                CourseEntity course5 = new CourseEntity(5L, "JavaScript for Beginners", "Learn JavaScript from scratch", new Date(), new Date(), new BigDecimal("99.99"), "def-course.jpg", category4);
                CourseEntity course6 = new CourseEntity(6L, "Database Fundamentals", "Understanding databases concepts", new Date(), new Date(), new BigDecimal("149.99"), "def-course.jpg", category5);
                CourseEntity course7 = new CourseEntity(7L,"UI/UX Design Principles", "Basics of user interface and user experience design", new Date(), new Date(), new BigDecimal("199.99"), "def-course.jpg", category6);
                CourseEntity course8 = new CourseEntity(8L, "Cybersecurity Essentials", "Introduction to cybersecurity", new Date(), new Date(), new BigDecimal("199.99"), "def-course.jpg", category7);
                CourseEntity course9 = new CourseEntity(9L, "Cloud Computing Fundamentals", "Basic concepts of cloud computing", new Date(), new Date(), new BigDecimal("249.99"), "def-course.jpg", category9);

                courseRepository.saveAll(List.of(course1, course2, course3, course4, course5, course6, course7, course8, course9));

                logger.info("Base de datos inicializada con cursos.");
            } else {
                logger.info("La base de datos ya está inicializada.");
            }
        }catch (Exception e){
            logger.error("Error al inicializar la base de datos: " + e.getMessage());
        }
    }
}
