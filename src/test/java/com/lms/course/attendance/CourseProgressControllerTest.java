package com.lms.course.attendance;

import com.lms.course.dto.CourseSessionDTO;
import com.lms.course.dto.StudentAttendanceDTO;
import com.lms.course.controller.CourseProgressController;
import com.lms.course.dto.CourseProgressDTOs;
import com.lms.course.service.CourseProgressService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseProgressControllerTest {

    @Mock
    private CourseProgressService courseProgressService;

    @InjectMocks
    private CourseProgressController courseProgressController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSession() {
        CourseSessionDTO dto = CourseSessionDTO.builder()
                .courseId(1L)
                .outlineId(2L)
                .sessionName("Intro")
                .sessionDate(LocalDateTime.now())
                .duration(45)
                .build();

        CourseSessionDTO returnedDto = CourseSessionDTO.builder()
                .id(10L)
                .courseId(1L)
                .outlineId(2L)
                .sessionName("Intro")
                .sessionDate(dto.getSessionDate())
                .duration(45)
                .build();

        when(courseProgressService.createScheduledSession(dto)).thenReturn(returnedDto);

        ResponseEntity<CourseSessionDTO> response = courseProgressController.createSession(dto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10L, response.getBody().getId());
        verify(courseProgressService).createScheduledSession(dto);
    }

    @Test
    void testMarkAttendance() {
        StudentAttendanceDTO requestDto = StudentAttendanceDTO.builder()
                .studentId(1L)
                .courseSessionId(3L)
                .attendanceStatus("PRESENT")
                .markedBy("TEACHER")
                .build();

        StudentAttendanceDTO returnedDto = StudentAttendanceDTO.builder()
                .id(100L)
                .studentId(1L)
                .courseSessionId(3L)
                .attendanceStatus("PRESENT")
                .markedBy("TEACHER")
                .build();

        when(courseProgressService.markAttendance(requestDto)).thenReturn(returnedDto);

        ResponseEntity<StudentAttendanceDTO> response = courseProgressController.markAttendance(requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(100L, response.getBody().getId());
        verify(courseProgressService).markAttendance(requestDto);
    }

    @Test
    void testGetStudentProgressForOutline() {
        Long studentId = 1L;
        Long outlineId = 2L;

        CourseProgressDTOs dto = CourseProgressDTOs.builder()
                .id(50L)
                .studentId(studentId)
                .outlineId(outlineId)
                .courseId(10L)
                .status("IN_PROGRESS")
                .progressPercentage(50.0)
                .lastUpdated(LocalDateTime.now())
                .build();

        when(courseProgressService.getStudentProgressForOutline(studentId, outlineId)).thenReturn(dto);

        ResponseEntity<CourseProgressDTOs> response = courseProgressController.getStudentProgressForOutline(studentId, outlineId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(50L, response.getBody().getId());
        verify(courseProgressService).getStudentProgressForOutline(studentId, outlineId);
    }

    @Test
    void testGetOverallProgress() {
        Long studentId = 1L;
        Long courseId = 2L;
        Double progress = 75.0;

        when(courseProgressService.getOverallCourseProgress(studentId, courseId)).thenReturn(progress);

        ResponseEntity<Double> response = courseProgressController.getOverallProgress(studentId, courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(progress, response.getBody());
        verify(courseProgressService).getOverallCourseProgress(studentId, courseId);
    }
}

