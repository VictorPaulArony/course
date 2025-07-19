package com.lms.course.controller;

import com.lms.course.dto.CourseDTO;
import com.lms.course.service.CourseService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Create a new course
    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        CourseDTO createdCourse = courseService.addCourse(courseDTO);
        return ResponseEntity.status(201).body(createdCourse);
    }

    // Get a course by ID
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        Optional<CourseDTO> courseOpt = courseService.getCourseById(id);
        return courseOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all courses
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    // Update a course
    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseDTO courseDTO) {
        Optional<CourseDTO> updated = courseService.updateCourse(id, courseDTO);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a course
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        boolean deleted = courseService.deleteCourse(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
