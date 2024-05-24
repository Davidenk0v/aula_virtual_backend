package com.grupo2.aulavirtual.tests.entitiestest;

import com.grupo2.aulavirtual.entities.CategoryEntity;
import com.grupo2.aulavirtual.entities.CommentEntity;
import com.grupo2.aulavirtual.entities.CourseEntity;
import com.grupo2.aulavirtual.entities.SubjectsEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseEntityTest {

    @Test
    void testGettersAndSetters() {
        CategoryEntity category = new CategoryEntity();
        SubjectsEntity subject = new SubjectsEntity();
        CommentEntity comment = new CommentEntity();

        CourseEntity courseEntity = CourseEntity.builder()
                .idCourse(1L)
                .name("Java Programming")
                .description("Learn Java programming language")
                .startDate(Date.valueOf("2024-09-01"))
                .finishDate(Date.valueOf("2024-12-31"))
                .price(BigDecimal.valueOf(100))
                .urlImg("http://example.com/image.jpg")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .subjects(List.of(subject))
                .category(List.of(category))
                .usersId(List.of("user1", "user2"))
                .idTeacher("teacher1")
                .comments(List.of(comment))
                .build();

        assertEquals(1L, courseEntity.getIdCourse(), "idCourse getter must be equal to 1L");
        courseEntity.setIdCourse(2L);
        assertEquals(2L, courseEntity.getIdCourse(), "idCourse setter must be equal to 2L");

        assertEquals("Java Programming", courseEntity.getName(), "Name getter must be equal to 'Java Programming'");
        courseEntity.setName("Python Programming");
        assertEquals("Python Programming", courseEntity.getName(), "Name setter must be equal to 'Python Programming'");

        assertEquals("Learn Java programming language", courseEntity.getDescription(),
                "Description getter must be equal to 'Learn Java programming language'");
        courseEntity.setDescription("Learn Python programming language");
        assertEquals("Learn Python programming language", courseEntity.getDescription(),
                "Description setter must be equal to 'Learn Python programming language'");

        assertEquals(Date.valueOf("2024-09-01"), courseEntity.getStartDate(),
                "StartDate getter must be equal to '2024-09-01'");
        courseEntity.setStartDate(Date.valueOf("2025-01-01"));
        assertEquals(Date.valueOf("2025-01-01"), courseEntity.getStartDate(),
                "StartDate setter must be equal to '2025-01-01'");

        assertEquals(Date.valueOf("2024-12-31"), courseEntity.getFinishDate(),
                "FinishDate getter must be equal to '2024-12-31'");
        courseEntity.setFinishDate(Date.valueOf("2025-12-31"));
        assertEquals(Date.valueOf("2025-12-31"), courseEntity.getFinishDate(),
                "FinishDate setter must be equal to '2025-12-31'");

        assertEquals(BigDecimal.valueOf(100), courseEntity.getPrice(), "Price getter must be equal to 100");
        courseEntity.setPrice(BigDecimal.valueOf(200));
        assertEquals(BigDecimal.valueOf(200), courseEntity.getPrice(), "Price setter must be equal to 200");

        assertEquals("http://example.com/image.jpg", courseEntity.getUrlImg(), "URL Image getter must be equal to 'http://example.com/image.jpg'");
        courseEntity.setUrlImg("http://example.com/newimage.jpg");
        assertEquals("http://example.com/newimage.jpg", courseEntity.getUrlImg(), "URL Image setter must be equal to 'http://example.com/newimage.jpg'");

        assertNotNull(courseEntity.getCreatedDate(), "CreatedDate getter must not be null");
        LocalDateTime newCreatedDate = LocalDateTime.now().minusDays(1);
        courseEntity.setCreatedDate(newCreatedDate);
        assertEquals(newCreatedDate, courseEntity.getCreatedDate(), "CreatedDate setter must be equal to newCreatedDate");

        assertNotNull(courseEntity.getLastModifiedDate(), "LastModifiedDate getter must not be null");
        LocalDateTime newLastModifiedDate = LocalDateTime.now().minusDays(1);
        courseEntity.setLastModifiedDate(newLastModifiedDate);
        assertEquals(newLastModifiedDate, courseEntity.getLastModifiedDate(), "LastModifiedDate setter must be equal to newLastModifiedDate");

        assertNotNull(courseEntity.getSubjects(), "Subjects getter must not be null");
        assertEquals(1, courseEntity.getSubjects().size(), "Subjects size must be equal to 1");
        courseEntity.setSubjects(List.of(subject, new SubjectsEntity()));
        assertEquals(2, courseEntity.getSubjects().size(), "Subjects setter must update the size to 2");

        assertNotNull(courseEntity.getCategory(), "Category getter must not be null");
        assertEquals(1, courseEntity.getCategory().size(), "Category size must be equal to 1");
        courseEntity.setCategory(List.of(category, new CategoryEntity()));
        assertEquals(2, courseEntity.getCategory().size(), "Category setter must update the size to 2");

        assertNotNull(courseEntity.getUsersId(), "UsersId getter must not be null");
        assertEquals(2, courseEntity.getUsersId().size(), "UsersId size must be equal to 2");
        courseEntity.setUsersId(List.of("user1", "user2", "user3"));
        assertEquals(3, courseEntity.getUsersId().size(), "UsersId setter must update the size to 3");

        assertEquals("teacher1", courseEntity.getIdTeacher(), "IdTeacher getter must be equal to 'teacher1'");
        courseEntity.setIdTeacher("teacher2");
        assertEquals("teacher2", courseEntity.getIdTeacher(), "IdTeacher setter must be equal to 'teacher2'");

        assertNotNull(courseEntity.getComments(), "Comments getter must not be null");
        assertEquals(1, courseEntity.getComments().size(), "Comments size must be equal to 1");
        courseEntity.setComments(List.of(comment, new CommentEntity()));
        assertEquals(2, courseEntity.getComments().size(), "Comments setter must update the size to 2");
    }

    @Test
    void testNotNull() {
        CourseEntity courseEntity = new CourseEntity();
        assertNotNull(courseEntity);
    }
}
