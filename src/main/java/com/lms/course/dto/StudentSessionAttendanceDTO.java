package com.lms.course.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSessionAttendanceDTO {

    private Long id;

    @NotNull(message = "Student ID must not be null")
    private Long studentId;

    @NotNull(message = "Session ID must not be null")
    private Long sessionId;

    @NotNull(message = "Attendance status must not be null")
    private AttendanceStatus status;

    @NotNull(message = "MarkedByTeacher flag must not be null")
    private Boolean markedByTeacher;

    public enum AttendanceStatus {
        PRESENT,
        ABSENT,
        NOT_MARKED
    }
}
