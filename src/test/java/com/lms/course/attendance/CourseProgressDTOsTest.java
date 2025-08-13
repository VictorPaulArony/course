package com.lms.course.attendance;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.lms.course.dto.CourseProgressDTOs;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CourseProgressDTOsTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDTO() {
        CourseProgressDTOs dto = CourseProgressDTOs.builder()
                .id(1L)
                .studentId(101L)
                .outlineId(202L)
                .courseId(303L)
                .outlineTitle("Intro to Algorithms")
                .completedSessionsCount(8)
                .totalExpectedSessions(10)
                .progressPercentage(80.0)
                .status("IN_PROGRESS")
                .lastUpdated(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<CourseProgressDTOs>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Expected no validation violations");
    }

    @Test
    void testMissingRequiredFields() {
        CourseProgressDTOs dto = new CourseProgressDTOs(); // All fields null

        Set<ConstraintViolation<CourseProgressDTOs>> violations = validator.validate(dto);

        assertEquals(5, violations.size(), "Expected 5 validation violations");

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("studentId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("outlineId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("courseId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("status")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastUpdated")));
    }

    @Test
    void testInvalidIds() {
        CourseProgressDTOs dto = CourseProgressDTOs.builder()
                .studentId(0L) // Invalid: < 1
                .outlineId(-5L) // Invalid: < 1
                .courseId(0L) // Invalid: < 1
                .status("ACTIVE")
                .lastUpdated(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<CourseProgressDTOs>> violations = validator.validate(dto);
        assertEquals(3, violations.size());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("studentId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("outlineId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("courseId")));
    }

    @Test
    void testBlankStatus() {
        CourseProgressDTOs dto = CourseProgressDTOs.builder()
                .studentId(1L)
                .outlineId(2L)
                .courseId(3L)
                .status(" ") // Invalid: blank
                .lastUpdated(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<CourseProgressDTOs>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("status")));
    }

    @Test
    void testSettersAndGetters() {
        CourseProgressDTOs dto = new CourseProgressDTOs();
        LocalDateTime now = LocalDateTime.now();

        dto.setId(10L);
        dto.setStudentId(20L);
        dto.setOutlineId(30L);
        dto.setCourseId(40L);
        dto.setOutlineTitle("Database Systems");
        dto.setCompletedSessionsCount(5);
        dto.setTotalExpectedSessions(8);
        dto.setProgressPercentage(62.5);
        dto.setStatus("COMPLETED");
        dto.setLastUpdated(now);

        assertEquals(10L, dto.getId());
        assertEquals(20L, dto.getStudentId());
        assertEquals(30L, dto.getOutlineId());
        assertEquals(40L, dto.getCourseId());
        assertEquals("Database Systems", dto.getOutlineTitle());
        assertEquals(5, dto.getCompletedSessionsCount());
        assertEquals(8, dto.getTotalExpectedSessions());
        assertEquals(62.5, dto.getProgressPercentage());
        assertEquals("COMPLETED", dto.getStatus());
        assertEquals(now, dto.getLastUpdated());
    }
}
