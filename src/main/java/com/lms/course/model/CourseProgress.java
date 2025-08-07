package com.lms.course.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;
import lombok.*;

//Tracks progress per course outline for a student.
@Entity
@Table(name = "course_progress", uniqueConstraints = @UniqueConstraint(columnNames = { "student_id", "outline_id" }))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "outline_id", nullable = false)
    private CourseOutline outline;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "completed_sessions_count", nullable = false)
    private Integer completedSessionsCount = 0;

    @Column(name = "progress_percentage", nullable = false)
    private Double progressPercentage = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.NOT_STARTED;

    @UpdateTimestamp
    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    public enum Status {
        COMPLETED, IN_PROGRESS, NOT_STARTED
    }
}
