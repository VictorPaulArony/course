package com.lms.course.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseEnrollmentDTO {
    private Long id;
    
    @NotNull(message = "Student ID is required")
    private Long studentId;
    
    private Long courseId;
    
    private LocalDateTime enrollmentDate;
    
    private String paymentStatus;
    
    private String paymentMethod;

    private BigDecimal amountPaidNow;

    private BigDecimal amountRemaining;
    

}
