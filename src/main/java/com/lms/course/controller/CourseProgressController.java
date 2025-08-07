package com.lms.course.controller;

import com.lms.course.dto.CourseSessionDTO;
import com.lms.course.dto.CourseProgressDTOs;
import com.lms.course.dto.StudentAttendanceDTO;
import com.lms.course.service.CourseProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class CourseProgressController {

    private final CourseProgressService courseProgressService;

    @PostMapping("/schedule-session")
    public ResponseEntity<CourseSessionDTO> createSession(@RequestBody CourseSessionDTO dto) {
        return ResponseEntity.ok(courseProgressService.createScheduledSession(dto));
    }

    @PostMapping("/mark-attendance")
    public ResponseEntity<StudentAttendanceDTO> markAttendance(@RequestBody StudentAttendanceDTO dto) {
        return ResponseEntity.ok(courseProgressService.markAttendance(dto));
    }

    @GetMapping("/student-outline-progress")
    public ResponseEntity<CourseProgressDTOs> getStudentProgressForOutline(
            @RequestParam Long studentId,
            @RequestParam Long outlineId) {
        return ResponseEntity.ok(courseProgressService.getStudentProgressForOutline(studentId, outlineId));
    }

    @GetMapping("/student-overall-progress")
    public ResponseEntity<Double> getOverallProgress(
            @RequestParam Long studentId,
            @RequestParam Long courseId) {
        return ResponseEntity.ok(courseProgressService.getOverallCourseProgress(studentId, courseId));
    }
}

