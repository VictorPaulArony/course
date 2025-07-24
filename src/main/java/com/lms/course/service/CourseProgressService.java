package com.lms.course.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lms.course.dto.CourseProgressDTOs;
import com.lms.course.model.Course;
import com.lms.course.model.CourseOutline;
import com.lms.course.model.CourseProgress;
import com.lms.course.repository.CourseProgressRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseProgressService {

    private final CourseProgressRepository courseProgressRepository;

    // Utility: Entity -> DTO
    private CourseProgressDTOs.CourseProgressDTO mapToDTO(CourseProgress progress) {
        return CourseProgressDTOs.CourseProgressDTO.builder()
                .id(progress.getId())
                .studentId(progress.getStudentId())
                .outlineId(progress.getOutlineId().getId())
                .courseId(progress.getCourseId().getId())
                .status(progress.getStatus().name())
                .lastUpdated(progress.getLastUpdated())
                .build();
    }

    // CREATE
    public CourseProgressDTOs.CourseProgressDTO createProgressStatus(CourseProgressDTOs.CreateProgressStatusDTO dto) {
        if (courseProgressRepository.existsByStudentIdAndOutlineId(dto.getStudentId(), dto.getOutlineId())) {
            throw new IllegalStateException("Progress already exists for this student and outline");
        }

        CourseProgress progress = new CourseProgress();
        progress.setStudentId(dto.getStudentId());
        progress.setOutlineId(new CourseOutline(dto.getOutlineId()));
        progress.setCourseId(new Course(dto.getCourseId()));


        if (dto.getStatus() != null) {
            progress.setStatus(CourseProgress.Status.valueOf(dto.getStatus().toUpperCase()));
        }

        return mapToDTO(courseProgressRepository.save(progress));
    }

    // GET by ID
    public CourseProgressDTOs.CourseProgressDTO getProgressById(Long id) {
        CourseProgress progress = courseProgressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Progress not found with ID: " + id));
        return mapToDTO(progress);
    }

    // UPDATE status
    public CourseProgressDTOs.CourseProgressDTO updateProgressStatus(Long id, CourseProgressDTOs.CourseProgressUpdateDTO dto) {
        CourseProgress progress = courseProgressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Progress not found with ID: " + id));

        progress.setStatus(CourseProgress.Status.valueOf(dto.getStatus().toUpperCase()));
        return mapToDTO(courseProgressRepository.save(progress));
    }

    // LIST all
    public List<CourseProgressDTOs.CourseProgressDTO> getAllProgress() {
        return courseProgressRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
