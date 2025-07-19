package com.lms.course.service;

import com.lms.course.dto.CourseEnrollmentDTO;
import com.lms.course.model.Course;
import com.lms.course.model.CourseEnrollment;
import com.lms.course.repository.CourseEnrollmentRepository;
import com.lms.course.repository.CourseRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseEnrollmentService {

    @Autowired
    private CourseEnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public CourseEnrollmentDTO enrollStudent(CourseEnrollmentDTO dto) {
        if (dto.getStudentId() == null || dto.getCourseId() == null) {
            throw new IllegalArgumentException("Student ID and Course ID must be provided.");
        }

        // Prevent duplicate enrollment
        enrollmentRepository.findByStudentIdAndCourseId(dto.getStudentId(), dto.getCourseId())
                .ifPresent(e -> {
                    throw new IllegalStateException("Student is already enrolled in this course.");
                });

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + dto.getCourseId()));

        CourseEnrollment enrollment = new CourseEnrollment();
        enrollment.setStudentId(dto.getStudentId());
        enrollment.setCourse(course);
        enrollment.setPaymentStatus(parsePaymentStatus(dto.getPaymentStatus()));
        enrollment.setPaymentMethod(parsePaymentMethod(dto.getPaymentMethod()));

        CourseEnrollment saved = enrollmentRepository.save(enrollment);
        return mapToDTO(saved);
    }

    public List<CourseEnrollmentDTO> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<CourseEnrollmentDTO> getEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CourseEnrollmentDTO> getEnrollment(Long studentId, Long courseId) {
        return enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .map(this::mapToDTO);
    }

    public Long countCompletedEnrollments(Long courseId) {
        return enrollmentRepository.countCompletedEnrollmentsByCourseId(courseId);
    }

    public List<CourseEnrollmentDTO> getEnrollmentsByTeacher(Long teacherId) {
        return enrollmentRepository.findEnrollmentsByTeacherId(teacherId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // ========== Mapping Methods ==========

    private CourseEnrollmentDTO mapToDTO(CourseEnrollment entity) {
        CourseEnrollmentDTO dto = new CourseEnrollmentDTO();
        dto.setId(entity.getId());
        dto.setStudentId(entity.getStudentId());
        dto.setCourseId(entity.getCourse().getId());
        dto.setEnrollmentDate(entity.getEnrollmentDate());
        dto.setPaymentStatus(entity.getPaymentStatus().name());
        dto.setPaymentMethod(entity.getPaymentMethod().name());
        return dto;
    }

    private CourseEnrollment.PaymentStatus parsePaymentStatus(String status) {
        try {
            return CourseEnrollment.PaymentStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            return CourseEnrollment.PaymentStatus.PENDING;
        }
    }

    private CourseEnrollment.PaymentMethod parsePaymentMethod(String method) {
        try {
            return CourseEnrollment.PaymentMethod.valueOf(method.toUpperCase());
        } catch (Exception e) {
            return CourseEnrollment.PaymentMethod.CREDIT_CARD;
        }
    }
}
