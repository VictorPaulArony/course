package com.lms.course.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.*;

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

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getPaymentAccount() { return paymentAccount; }
    public void setPaymentAccount(String paymentAccount) { this.paymentAccount = paymentAccount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<CourseOutlineDTO> getCourseOutlines() { return courseOutlines; }
    public void setCourseOutlines(List<CourseOutlineDTO> courseOutlines) { this.courseOutlines = courseOutlines; }
    public List<CourseEnrollmentDTO> getEnrollments() { return enrollments; }
    public void setEnrollments(List<CourseEnrollmentDTO> enrollments) { this.enrollments = enrollments; 
}
}
