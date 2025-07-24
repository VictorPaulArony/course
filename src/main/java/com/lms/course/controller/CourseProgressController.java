package com.lms.course.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lms.course.dto.CourseProgressDTOs.*;
import com.lms.course.service.CourseProgressService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/course-progress")
@RequiredArgsConstructor
public class CourseProgressController {

    private final CourseProgressService courseProgressService;

    @PostMapping
    public ResponseEntity<CourseProgressDTO> create(@Valid @RequestBody CreateProgressStatusDTO dto) {
        return ResponseEntity.ok(courseProgressService.createProgressStatus(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseProgressDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(courseProgressService.getProgressById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseProgressDTO> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody CourseProgressUpdateDTO dto) {
        return ResponseEntity.ok(courseProgressService.updateProgressStatus(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<CourseProgressDTO>> getAll() {
        return ResponseEntity.ok(courseProgressService.getAllProgress());
    }
}
