package com.lms.course.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lms.course.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
    
    List<Course> findByTeacherId(Long teacherId);
    List<Course> findByTitleContaining(String title);

    List<Course> findByModeAndStartDateAfter(Course.Mode mode, LocalDateTime date);

    @Query("SELECT c FROM Course c WHERE c.title LIKE %:title% OR c.description LIKE %:title%")
    List<Course> findByTitleOrDescriptionContaining(String title);

    @Query("SELECT c FROM Course c WHERE c.startDate >= :startDate AND c.endDate <= :endDate")
    List<Course> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                 @Param("endDate") LocalDateTime endDate);

    @Query("SELECT c FROM Course c WHERE c.price BETWEEN :minPrice AND :maxPrice")
    List<Course> findByPriceRange(@Param("minPrice") Double minPrice,
                                  @Param("maxPrice") Double maxPrice);
}

