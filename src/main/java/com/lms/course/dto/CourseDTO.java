package com.lms.course.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    private Long id;
    
    @NotBlank(message = "Course title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;
    
    private String description;
    
    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1")
    private Integer duration;
    
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;
    
    @NotNull(message = "End date is required")
    private LocalDateTime endDate;
    
    @NotBlank(message = "Mode is required")
    private String mode;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be non-negative")
    private BigDecimal price;
    
    private Long teacherId;
    
    private String paymentMethod;
    
    @Size(max = 255, message = "Payment account must not exceed 255 characters")
    private String paymentAccount;
    
    private LocalDateTime createdAt;
    
    private List<CourseOutlineDTO> courseOutlines;
    
    private List<CourseEnrollmentDTO> enrollments;

}
