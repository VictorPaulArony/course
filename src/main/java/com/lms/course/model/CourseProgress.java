package com.lms.course.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_progress", uniqueConstraints = @UniqueConstraint(columnNames = { "student_id", "outline_id" }))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "outline_id", nullable = false)
    private CourseOutline outlineId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course courseId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.NOT_STARTED;

    @UpdateTimestamp
    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    public enum Status {
        COMPLETED, IN_PROGRESS, NOT_STARTED, REVIEWED
    }

}
