package com.lms.course.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_session", uniqueConstraints = @UniqueConstraint(columnNames = { "student_id", "outline_id" }))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outline_id", nullable = false)
    private CourseOutline outlineId;
    
    @UpdateTimestamp
    @Column(name = "session_date", nullable = false)
    private LocalDateTime sessionDate;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.NOT_STARTED;

    public enum Status {
        COMPLETED, STARTED, NOT_STARTED
    }
}
