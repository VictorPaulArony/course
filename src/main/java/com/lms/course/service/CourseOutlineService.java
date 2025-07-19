package com.lms.course.service;

import com.lms.course.dto.CourseOutlineDTO;
import com.lms.course.model.Course;
import com.lms.course.model.CourseOutline;
import com.lms.course.repository.CourseOutlineRepository;
import com.lms.course.repository.CourseRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseOutlineService {

    @Autowired
    private CourseOutlineRepository outlineRepository;

    @Autowired
    private CourseRepository courseRepository;

    // Create outline
    @Transactional
    public CourseOutlineDTO createOutline(Long courseId, CourseOutlineDTO dto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        CourseOutline outline = new CourseOutline();
        outline.setCourse(course);
        outline.setTitle(dto.getTitle());
        outline.setDescription(dto.getDescription());
        outline.setOrderIndex(dto.getOrderIndex());
        outline.setContentType(parseContentType(dto.getContentType()));
        outline.setContentUrl(dto.getContentUrl());
        outline.setDuration(dto.getDuration());

        return mapToDTO(outlineRepository.save(outline));
    }

    // Get all outlines for course
    public List<CourseOutlineDTO> getOutlinesByCourse(Long courseId) {
        return outlineRepository.findByCourseIdOrderByOrderIndex(courseId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get outline by ID
    public Optional<CourseOutlineDTO> getOutlineById(Long id) {
        return outlineRepository.findById(id).map(this::mapToDTO);
    }

    // Update outline
    @Transactional
    public Optional<CourseOutlineDTO> updateOutline(Long id, CourseOutlineDTO dto) {
        return outlineRepository.findById(id).map(outline -> {
            outline.setTitle(dto.getTitle());
            outline.setDescription(dto.getDescription());
            outline.setOrderIndex(dto.getOrderIndex());
            outline.setContentType(parseContentType(dto.getContentType()));
            outline.setContentUrl(dto.getContentUrl());
            outline.setDuration(dto.getDuration());
            return mapToDTO(outlineRepository.save(outline));
        });
    }

    // Delete outline
    public boolean deleteOutline(Long id) {
        if (!outlineRepository.existsById(id)) return false;
        outlineRepository.deleteById(id);
        return true;
    }

    // Get total duration of a course
    public Integer getTotalDurationByCourseId(Long courseId) {
        return outlineRepository.getTotalDurationByCourseId(courseId);
    }

    // ========== Helpers ==========

    private CourseOutline.ContentType parseContentType(String type) {
        try {
            return CourseOutline.ContentType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid content type: " + type);
        }
    }

    private CourseOutlineDTO mapToDTO(CourseOutline entity) {
        CourseOutlineDTO dto = new CourseOutlineDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setOrderIndex(entity.getOrderIndex());
        dto.setContentType(entity.getContentType().name());
        dto.setContentUrl(entity.getContentUrl());
        dto.setDuration(entity.getDuration());
        return dto;
    }
}
