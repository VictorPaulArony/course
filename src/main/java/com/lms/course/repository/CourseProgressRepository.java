package com.lms.course.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.course.model.CourseProgress;

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Long> {
    Optional<CourseProgress> findByStudentIdAndOutlineId(Long studentId, Long outlineId);
    boolean existsByStudentIdAndOutlineId(Long studentId, Long outlineId);
}
