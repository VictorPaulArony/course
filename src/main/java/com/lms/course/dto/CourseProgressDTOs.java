package com.lms.course.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseProgressDTOs {

        private Long id;

        @NotNull(message = "Student ID is required")
        @Min(value = 1, message = "Student ID must be a positive number.")
        private Long studentId;

        @NotNull(message = "Outline ID is required")
        @Min(value = 1, message = "course ID must be a positive number.")
        private Long outlineId;

        @NotNull(message = "Course ID is required")
        @Min(value = 1, message = "course Outline ID must be a positive number.")
        private Long courseId;

        private String outlineTitle;
        private Integer completedSessionsCount;
        private Integer totalExpectedSessions;
        private Double progressPercentage;

        @NotBlank(message = "Status must not be blank.")
        private String status;

        @NotNull(message = "last update must not be null.")
        private LocalDateTime lastUpdated;
    
}