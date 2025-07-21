package com.lms.course.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer duration;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Mode mode;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    @Column( name = "teacher_id", nullable = false)
    private Long teacherId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;

    @Column(name = "payment_account", length = 255)
    private String paymentAccount;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CourseOutline> courseOutline;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CourseEnrollment> enrollments;

    public enum Mode {
        ONLINE, OFFLINE, HYBRID
    }

    public enum PaymentMethod {
        CREDIT_CARD, PAYPAL, BANK_TRANSFER
    }
    // Getters and Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public Integer getDuration() {return duration;}
    public void setDuration(Integer duration) {this.duration = duration;}
    public LocalDateTime getStartDate() {return startDate;}
    public void setStartDate(LocalDateTime startDate) {this.startDate = startDate;}
    public LocalDateTime getEndDate() {return endDate;}
    public void setEndDate(LocalDateTime endDate) {this.endDate = endDate;}
    public Mode getMode() {return mode;}
    public void setMode(Mode mode) {this.mode = mode;}
    public BigDecimal getPrice() {return price;}
    public void setPrice(BigDecimal price) {this.price = price;}
    public Long getTeacherId() {return teacherId;}
    public void setTeacherId(Long teacherId) {this.teacherId = teacherId;}
    public PaymentMethod getPaymentMethod() {return paymentMethod;}
    public void setPaymentMethod(PaymentMethod paymentMethod) {this.paymentMethod = paymentMethod;}
    public String getPaymentAccount() {return paymentAccount;}
    public void setPaymentAccount(String paymentAccount) {this.paymentAccount = paymentAccount;}
    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
    public List<CourseOutline> getCourseOutline() {return courseOutline;}
    public void setCourseOutline(List<CourseOutline> courseOutline) {this.courseOutline = courseOutline;}
    public List<CourseEnrollment> getEnrollments() {return enrollments;}
    public void setEnrollments(List<CourseEnrollment> enrollments) {this.enrollments = enrollments;}
    
}
