package com.lms.course.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.course.dto.CourseDTO;
import com.lms.course.model.Course;
import com.lms.course.repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // CREATE
    @Transactional
    public CourseDTO addCourse(CourseDTO courseDTO) {
        Course course = mapToEntity(courseDTO);
        course.setCreatedAt(LocalDateTime.now());
        Course savedCourse = courseRepository.save(course);
        return mapToDTO(savedCourse);
    }

    // READ by ID
    public Optional<CourseDTO> getCourseById(Long id) {
        return courseRepository.findById(id).map(this::mapToDTO);
    }

    // LIST all
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // DELETE
    @Transactional
    public boolean deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // UPDATE
    @Transactional
    public Optional<CourseDTO> updateCourse(Long id, CourseDTO updatedDTO) {
        return courseRepository.findById(id).map(existing -> {
            existing.setTitle(updatedDTO.getTitle());
            existing.setDescription(updatedDTO.getDescription());
            existing.setDuration(updatedDTO.getDuration());
            existing.setStartDate(updatedDTO.getStartDate());
            existing.setEndDate(updatedDTO.getEndDate());
            existing.setMode(Course.Mode.valueOf(updatedDTO.getMode().toUpperCase()));
            existing.setPrice(updatedDTO.getPrice());
            existing.setTeacherId(updatedDTO.getTeacherId());
            existing.setPaymentMethod(Course.PaymentMethod.valueOf(updatedDTO.getPaymentMethod().toUpperCase()));
            existing.setPaymentAccount(updatedDTO.getPaymentAccount() != null && !updatedDTO.getPaymentAccount().isEmpty() 
                    ? updatedDTO.getPaymentAccount() 
                    : "N/A");

            return mapToDTO(courseRepository.save(existing));
        });
    }

    // Utility: DTO -> Entity
    private Course mapToEntity(CourseDTO dto) {
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setDuration(dto.getDuration());
        course.setStartDate(dto.getStartDate());
        course.setEndDate(dto.getEndDate());
        course.setMode(Course.Mode.valueOf(dto.getMode().toUpperCase()));
        course.setPrice(dto.getPrice());
        course.setTeacherId(dto.getTeacherId());
        course.setPaymentMethod(Course.PaymentMethod.valueOf(dto.getPaymentMethod().toUpperCase()));
        course.setPaymentAccount(dto.getPaymentAccount() != null && !dto.getPaymentAccount().isEmpty() 
                ? dto.getPaymentAccount() 
                : "N/A");
        return course;
    }

    // Utility: Entity -> DTO
    private CourseDTO mapToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setDuration(course.getDuration());
        dto.setStartDate(course.getStartDate());
        dto.setEndDate(course.getEndDate());
        dto.setMode(course.getMode().toString());
        dto.setPrice(course.getPrice());
        dto.setTeacherId(course.getTeacherId());
        dto.setPaymentMethod(course.getPaymentMethod().toString());
        dto.setPaymentAccount(course.getPaymentAccount());
        dto.setCreatedAt(course.getCreatedAt());
        return dto;
    }
}
