package com.lms.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.course.model.Course;
import com.lms.course.model.CourseOutline;
import com.lms.course.model.CourseSession;
import com.lms.course.model.Student;

public interface CourseSessionRepository extends JpaRepository<CourseSession, Long> {
    List<CourseSession> findByStudentAndOutline(Student student, Course course);
    List<CourseSession> findByStudentId(Long studentId);
    List<CourseSession> findByOutline(CourseOutline outline);
}
