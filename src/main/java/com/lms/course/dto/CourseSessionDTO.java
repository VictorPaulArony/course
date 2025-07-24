package com.lms.course.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSessionDTO {
    private Long id;
    private Long studentId;
    private Long courseId;
    private Long outlineId;
    private LocalDateTime sessionDate;
    private Integer duration;
    private String status;
}
