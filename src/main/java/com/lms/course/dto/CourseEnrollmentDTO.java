package com.lms.course.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;


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
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDateTime enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public BigDecimal getAmountPaidNow() { return amountPaidNow; }
    public void setAmountPaidNow(BigDecimal amountPaidNow) { this.amountPaidNow = amountPaidNow; }
    public BigDecimal getAmountRemaining() { return amountRemaining; }
    public void setAmountRemaining(BigDecimal amountRemaining) { this.amountRemaining = amountRemaining; }
}
