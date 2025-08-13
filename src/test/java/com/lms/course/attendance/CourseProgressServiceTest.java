package com.lms.course.attendance;

import com.lms.course.dto.CourseSessionDTO;
import com.lms.course.dto.StudentAttendanceDTO;
import com.lms.course.model.*;
import com.lms.course.repository.*;
import com.lms.course.service.CourseProgressService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseProgressServiceTest {

    private CourseProgressService service;
    private CourseSessionRepository courseSessionRepository;
    private CourseProgressRepository courseProgressRepository;
    private StudentAttendanceRepository studentAttendanceRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private CourseOutlineRepository courseOutlineRepository;

    @BeforeEach
    void setUp() {
        courseSessionRepository = mock(CourseSessionRepository.class);
        courseProgressRepository = mock(CourseProgressRepository.class);
        studentAttendanceRepository = mock(StudentAttendanceRepository.class);
        studentRepository = mock(StudentRepository.class);
        courseRepository = mock(CourseRepository.class);
        courseOutlineRepository = mock(CourseOutlineRepository.class);

        service = new CourseProgressService(
        courseSessionRepository,
        courseProgressRepository,
        studentAttendanceRepository,
        studentRepository,
        courseRepository,
        courseOutlineRepository
    );
    }

    @Test
    void testCreateScheduledSession() {
        Course course = new Course();
        course.setId(1L);
        CourseOutline outline = new CourseOutline();
        outline.setId(2L);
        outline.setCourse(course);

        CourseSessionDTO dto = CourseSessionDTO.builder()
                .courseId(1L)
                .outlineId(2L)
                .sessionName("Test Session")
                .sessionDate(LocalDateTime.now())
                .duration(60)
                .build();

        CourseSession savedSession = new CourseSession();
        savedSession.setId(10L);
        savedSession.setCourse(course);
        savedSession.setOutline(outline);
        savedSession.setSessionName("Test Session");
        savedSession.setSessionDate(dto.getSessionDate());
        savedSession.setDuration(60);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseOutlineRepository.findById(2L)).thenReturn(Optional.of(outline));
        when(courseSessionRepository.save(any(CourseSession.class))).thenReturn(savedSession);

        CourseSessionDTO result = service.createScheduledSession(dto);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(1L, result.getCourseId());
        assertEquals(2L, result.getOutlineId());
        verify(courseSessionRepository).save(any(CourseSession.class));
    }

    @Test
    void testMarkAttendance_NewAttendance() {
        Student student = new Student();
        student.setId(1L);
        Course course = new Course();
        CourseOutline outline = new CourseOutline();
        outline.setId(2L);
        outline.setCourse(course);
        outline.setTotalExpectedSessions(5);

        CourseSession session = new CourseSession();
        session.setId(3L);
        session.setOutline(outline);

        StudentAttendanceDTO dto = StudentAttendanceDTO.builder()
                .studentId(1L)
                .courseSessionId(3L)
                .attendanceStatus("PRESENT")
                .markedBy("TEACHER")
                .build();

        StudentAttendance savedAttendance = new StudentAttendance();
        savedAttendance.setId(100L);
        savedAttendance.setStudent(student);
        savedAttendance.setCourseSession(session);
        savedAttendance.setAttendanceStatus(StudentAttendance.AttendanceStatus.PRESENT);
        savedAttendance.setMarkedBy(StudentAttendance.MarkedBy.TEACHER);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseSessionRepository.findById(3L)).thenReturn(Optional.of(session));
        when(studentAttendanceRepository.findByStudentAndCourseSession(student, session)).thenReturn(Optional.empty());
        when(studentAttendanceRepository.save(any(StudentAttendance.class))).thenReturn(savedAttendance);
        when(courseProgressRepository.findByStudentAndOutline(student, outline)).thenReturn(Optional.empty());
        when(studentAttendanceRepository.countByStudentAndCourseSession_OutlineAndAttendanceStatus(
                student, outline, StudentAttendance.AttendanceStatus.PRESENT)).thenReturn(1L);

        StudentAttendanceDTO result = service.markAttendance(dto);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("PRESENT", result.getAttendanceStatus());
        assertEquals("TEACHER", result.getMarkedBy());
        verify(studentAttendanceRepository).save(any(StudentAttendance.class));
        verify(courseProgressRepository).save(any(CourseProgress.class));
    }

    @Test
    void testGetOverallCourseProgress() {
        Student student = new Student();
        student.setId(1L);
        Course course = new Course();
        course.setId(2L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(2L)).thenReturn(Optional.of(course));
        when(courseOutlineRepository.countByCourse(course)).thenReturn(2L);
        when(courseProgressRepository.findProgressPercentagesByStudentAndCourse(student, course))
                .thenReturn(Collections.singletonList(80.0));

        Double result = service.getOverallCourseProgress(1L, 2L);

        assertNotNull(result);
        assertEquals(40.0, result); // 80 / 2 outlines = 40%
        verify(courseProgressRepository).findProgressPercentagesByStudentAndCourse(student, course);
    }
}

