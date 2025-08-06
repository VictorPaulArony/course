package com.lms.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.course.model.CourseSession;

public interface CourseSessionRepository extends JpaRepository<CourseSession, Long> {
    List<CourseSession> findByStudentIdAndCourseId(Long studentId, Long courseId);
    List<CourseSession> findByStudentId(Long studentId);
}
