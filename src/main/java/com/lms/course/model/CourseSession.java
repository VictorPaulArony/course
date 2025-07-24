package com.lms.course.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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
    private Course course_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outline_id", nullable = false)
    private CourseOutline outline_id;
    
    @UpdateTimestamp
    @Column(name = "session_date", nullable = false)
    private LocalDateTime sessionDate;

    @Column(nullable = false)
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.NOT_STARTED;

    public enum Status {
        COMPLETED, STARTED, NOT_STARTED
    }
}
