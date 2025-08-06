package com.lms.course.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseOutlineSessionDTO {

    private Long id;

    @NotNull(message = "Course outline ID must not be null")
    private Long outlineId;

    @NotNull(message = "Session date must not be null")
    private LocalDateTime sessionDate;

    @NotNull(message = "Duration must not be null")
    private Integer duration; // In minutes
}

