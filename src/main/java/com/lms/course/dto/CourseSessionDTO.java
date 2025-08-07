package com.lms.course.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for creating/updating scheduled Course Sessions
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseSessionDTO {
    private Long id;

    @NotNull(message = "Course ID is required")
    @Min(value = 1, message = "Course ID must be a positive number.")
    private Long courseId;

    @NotNull(message = "Outline ID is required")
    @Min(value = 1, message = "Outline ID must be a positive number.")
    private Long outlineId;

    @NotBlank(message = "Session name is required")
    private String sessionName;

    @NotNull(message = "Scheduled date is required")
    private LocalDateTime sessionDate;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be a positive number.")
    private Integer duration;
}
