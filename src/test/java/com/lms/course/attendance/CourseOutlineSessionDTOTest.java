package com.lms.course.attendance;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.lms.course.dto.CourseOutlineSessionDTO;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CourseOutlineSessionDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDTO() {
        CourseOutlineSessionDTO dto = new CourseOutlineSessionDTO(
                1L,
                101L,
                LocalDateTime.of(2025, 8, 13, 10, 0),
                90
        );

        Set<ConstraintViolation<CourseOutlineSessionDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Expected no validation violations");
    }

    @Test
    void testNullFields() {
        CourseOutlineSessionDTO dto = new CourseOutlineSessionDTO();

        Set<ConstraintViolation<CourseOutlineSessionDTO>> violations = validator.validate(dto);
        assertEquals(3, violations.size());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("outlineId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("sessionDate")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("duration")));
    }

    @Test
    void testSetterGetterFunctionality() {
        CourseOutlineSessionDTO dto = new CourseOutlineSessionDTO();
        LocalDateTime now = LocalDateTime.now();

        dto.setId(1L);
        dto.setOutlineId(200L);
        dto.setSessionDate(now);
        dto.setDuration(120);

        assertEquals(1L, dto.getId());
        assertEquals(200L, dto.getOutlineId());
        assertEquals(now, dto.getSessionDate());
        assertEquals(120, dto.getDuration());
    }
}

