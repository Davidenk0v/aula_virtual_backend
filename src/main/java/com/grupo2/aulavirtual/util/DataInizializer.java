package com.grupo2.aulavirtual.util;

import com.grupo2.aulavirtual.entities.CategoryEntity;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.repositories.CategoryRepository;
import com.grupo2.aulavirtual.repositories.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            courseRepository.save(new CourseEntity(
                    1L, "Web Development Fundamentals", "This is an introductory course to web development.", LocalDate.parse("2024-09-30"), LocalDate.parse("2024-06-01"),
                    199.99, "https://www.example.com/web_dev_fundamentals.jpg", LocalDateTime.now(), LocalDateTime.now(), category2, null
            ));
            courseRepository.save(new CourseEntity(
                    2L, "Python Programming for Beginners", "Learn the basics of programming with Python.", LocalDate.parse("2024-08-31"), LocalDate.parse("2024-07-05"),
                    149.99, "https://www.example.com/python_beginners.jpg", LocalDateTime.now(), LocalDateTime.now(), categoryRepository.findById(1L).orElse(null)
            ));
            courseRepository.save(new CourseEntity(
                    3L, "Data Analysis with Excel", "Master the art of data analysis with Excel.", LocalDate.parse("2024-10-31"), LocalDate.parse("2024-08-10"),
                    129.99, "https://www.example.com/excel_data_analysis.jpg", LocalDateTime.now(), LocalDateTime.now(), categoryRepository.findById(3L).orElse(null)
            ));
            courseRepository.save(new CourseEntity(
                    4L, "Adobe Photoshop for Beginners", "Create stunning graphics and designs with Adobe Photoshop.", LocalDate.parse("2024-11-30"), LocalDate.parse("2024-09-15"),
                    199.99, "https://www.example.com/photoshop_beginners.jpg", LocalDateTime.now(), LocalDateTime.now(), categoryRepository.findById(4L).orElse(null)
            ));
            courseRepository.save(new CourseEntity(
                    5L, "Front-End Web Development", "Learn how to build responsive websites with HTML, CSS, and JavaScript.", LocalDate.parse("2024-12-31"), LocalDate.parse("2024-10-01"),
                    249.99, "https://www.example.com/front_end_web_dev.jpg", LocalDateTime.now(), LocalDateTime.now(), categoryRepository.findById(2L).orElse(null)
            ));
            courseRepository.save(new CourseEntity(
                    6L, "Machine Learning Fundamentals", "Delve into the world of machine learning and artificial intelligence.", LocalDate.parse("2024-11-30"), LocalDate.parse("2024-11-15"),
                    299.99, "https://www.example.com/machine_learning_fundamentals.jpg", LocalDateTime.now(), LocalDateTime.now(), categoryRepository.findById(3L).orElse(null), null, null, null
            ));
            courseRepository.save(new CourseEntity(
                    7L, "Data Visualization with Tableau", "Learn how to create interactive data visualizations with Tableau.", LocalDate.parse("2024-12-31"), LocalDate.parse("2024-12-01"),
                    199.99, "https://www.example.com/tableau_data_visualization.jpg", LocalDateTime.now(), LocalDateTime.now(), categoryRepository.findById(3L).orElse(null)
            ));
            courseRepository.save(new CourseEntity(
                    8L, "Data Storytelling", "Master the art of storytelling with data.", LocalDate.parse("2025-01-31"), LocalDate.parse("2025-01-10"),
                    149.99, "https://www.example.com/data_storytelling.jpg", LocalDateTime.now(), LocalDateTime.now(), categoryRepository.findById(3L).orElse(null)
            ));
            courseRepository.save(new CourseEntity(
                    9L, "Cybersecurity Fundamentals", "Learn how to build and manage secure networks.", LocalDate.parse("2025-02-28"), LocalDate.parse("2025-02-15"),
                    249.99, "https://www.example.com/cybersecurity_fundamentals.jpg", LocalDateTime.now(), LocalDateTime.now(), categoryRepository.findById(1L).orElse(null)
            ));
            courseRepository.save(new CourseEntity(
                    10L, "Agile Project Management", "Master the art of project management with Agile methodologies.", LocalDate.parse("2025-03-31"), LocalDate.parse("2025-03-01"),
                    199.99, "https://www.example.com/agile_project_management.jpg", LocalDateTime.now(), LocalDateTime.now(), categoryRepository.findById(5L).orElse(null)
            ));
            System.out.println("Base de datos inicializada con cursos.");
        } else {
            System.out.println("La base de datos ya está inicializada.");
        }
    }
}
