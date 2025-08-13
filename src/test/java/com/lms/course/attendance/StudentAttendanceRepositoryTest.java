package com.lms.course.attendance;

import com.lms.course.model.CourseOutline;
import com.lms.course.model.CourseSession;
import com.lms.course.model.Student;
import com.lms.course.model.StudentAttendance;
import com.lms.course.model.StudentAttendance.AttendanceStatus;
import com.lms.course.repository.StudentAttendanceRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentAttendanceRepositoryTest {

    private StudentAttendanceRepository repository;

    private Student student;
    private CourseSession courseSession;
    private CourseOutline outline;

    @BeforeEach
    void setUp() {
        repository = mock(StudentAttendanceRepository.class);
        student = new Student(); // These would ideally be test stubs or mock data
        courseSession = new CourseSession();
        outline = new CourseOutline();
    }

    @Test
    void testFindByStudentAndCourseSession() {
        StudentAttendance mockAttendance = new StudentAttendance();
        when(repository.findByStudentAndCourseSession(student, courseSession))
                .thenReturn(Optional.of(mockAttendance));

        Optional<StudentAttendance> result = repository.findByStudentAndCourseSession(student, courseSession);

        assertTrue(result.isPresent());
        assertEquals(mockAttendance, result.get());
        verify(repository, times(1)).findByStudentAndCourseSession(student, courseSession);
    }

    @Test
    void testCountByStudentAndCourseSession_OutlineAndAttendanceStatus() {
        when(repository.countByStudentAndCourseSession_OutlineAndAttendanceStatus(
                student, outline, AttendanceStatus.PRESENT)).thenReturn(5L);

        Long count = repository.countByStudentAndCourseSession_OutlineAndAttendanceStatus(
                student, outline, AttendanceStatus.PRESENT);

        assertEquals(5L, count);
        verify(repository, times(1))
                .countByStudentAndCourseSession_OutlineAndAttendanceStatus(student, outline, AttendanceStatus.PRESENT);
    }
}

