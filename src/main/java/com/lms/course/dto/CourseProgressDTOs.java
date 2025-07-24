package com.lms.course.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CourseProgressDTOs {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CourseProgressDTO {

        private Long id;

        @NotNull(message = "Student ID is required")
        private Long studentId;
        @NotNull(message = "Outline ID is required")
        private Long outlineId;
        @NotNull(message = "Course ID is required")
        private Long courseId;
        private String status;
        private LocalDateTime lastUpdated;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CourseProgressUpdateDTO {
        @NotNull(message = "Status is required")
        private String status;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CreateProgressStatusDTO {
        @NotNull(message = "Student ID is required")
        private Long studentId;
        @NotNull(message = "Outline ID is required")
        private Long outlineId;
        @NotNull(message = "Course ID is required")
        private Long courseId;
        private String status;
    }
}