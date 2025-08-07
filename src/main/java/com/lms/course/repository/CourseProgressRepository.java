package com.lms.course.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lms.course.model.Course;
import com.lms.course.model.CourseOutline;
import com.lms.course.model.CourseProgress;
import com.lms.course.model.Student;

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Long> {
    Optional<CourseProgress> findByStudentAndOutline(Student student, CourseOutline outline);
    boolean existsByStudentIdAndOutlineId(Long studentId, Long outlineId);

    // Custom query to get progress percentages for a student across all outlines in a course
    @Query("SELECT cp.progressPercentage FROM CourseProgress cp WHERE cp.student = :student AND cp.course = :course")
    List<Double> findProgressPercentagesByStudentAndCourse(@Param("student") Student student, @Param("course") Course course);

}
