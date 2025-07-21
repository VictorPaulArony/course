package com.lms.course.controller;

import com.lms.course.dto.CourseOutlineDTO;
import com.lms.course.service.CourseOutlineService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses/{courseId}/outlines")
public class CourseOutlineController {

    @Autowired
    private CourseOutlineService courseOutlineService;

    // Create outline
    @PostMapping
    public ResponseEntity<CourseOutlineDTO> createOutline(
            @PathVariable Long courseId,
            @Valid @RequestBody CourseOutlineDTO dto) {
        return ResponseEntity.status(201).body(courseOutlineService.createOutline(courseId, dto));
    }

    // Get all outlines for course
    @GetMapping
    public ResponseEntity<List<CourseOutlineDTO>> getOutlines(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseOutlineService.getOutlinesByCourse(courseId));
    }

    // Get outline by ID
    @GetMapping("/{outlineId}")
    public ResponseEntity<CourseOutlineDTO> getOutlineById(@PathVariable Long outlineId) {
        Optional<CourseOutlineDTO> outline = courseOutlineService.getOutlineById(outlineId);
        return outline.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Update outline
    @PutMapping("/{outlineId}")
    public ResponseEntity<CourseOutlineDTO> updateOutline(
            @PathVariable Long outlineId,
            @Valid @RequestBody CourseOutlineDTO dto) {
        Optional<CourseOutlineDTO> updated = courseOutlineService.updateOutline(outlineId, dto);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Delete outline
    @DeleteMapping("/{outlineId}")
    public ResponseEntity<Void> deleteOutline(@PathVariable Long outlineId) {
        boolean deleted = courseOutlineService.deleteOutline(outlineId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Get total duration
    @GetMapping("/duration")
    public ResponseEntity<Integer> getTotalDuration(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseOutlineService.getTotalDurationByCourseId(courseId));
    }
}
