package com.lms.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lms.course.model.CourseOutline;

@Repository
public interface CourseOutlineRepository extends JpaRepository<CourseOutline, Long> {

    List<CourseOutline> findByCourseIdOrderByOrderIndex(Long courseId);

    List<CourseOutline> findByCourseIdAndOrderIndex(Long courseId, Integer orderIndex);

    List<CourseOutline> findByContentType(CourseOutline.ContentType contentType);

    @Query("SELECT co FROM CourseOutline co WHERE co.course.id = :courseId AND co.contentType = :contentType")
    List<CourseOutline> findByCourseIdAndContentType(@Param("courseId") Long courseId, 
                                                     @Param("contentType") CourseOutline.ContentType contentType);
    
    @Query("SELECT SUM(co.duration) FROM CourseOutline co WHERE co.course.id = :courseId")
    Integer getTotalDurationByCourseId(@Param("courseId") Long courseId);
    
}
