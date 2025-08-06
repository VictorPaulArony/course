package com.lms.course.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSessionDTO {
    
    private Long id;

    @NotNull(message = "Student ID must not be null.")
    @Min(value = 1, message = "Student ID must be a positive number.")
    private Long studentId;

    @NotNull(message = "Course ID must not be null.")
    @Min(value = 1, message = "Course ID must be a positive number.")
    private Long courseId;

    @NotNull(message = "Outline ID must not be null.")
    @Min(value = 1, message = "course outline ID must be a positive number.")
    private Long outlineId;

    @NotNull(message = "Session date must not be null.")
    private LocalDateTime sessionDate;

    @NotNull(message = "Duration must not be null.")
    @Min(value = 1, message = "Duration must be at least 1 minute.")
    private Integer duration;

    @NotBlank(message = "Status must not be blank.")
    private String status;
}
