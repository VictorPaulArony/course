package com.lms.course.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.course.model.CourseOutline;
import com.lms.course.model.CourseSession;
import com.lms.course.model.Student;
import com.lms.course.model.StudentAttendance;

@Repository
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, Long> {
    Optional<StudentAttendance> findByStudentAndCourseSession(Student student, CourseSession courseSession);

    // Custom query to count completed sessions for a student in a specific outline
    Long countByStudentAndCourseSession_OutlineAndAttendanceStatus(
            Student student, CourseOutline outline, StudentAttendance.AttendanceStatus status);
}
