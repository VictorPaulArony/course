package com.lms.course.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_outline", uniqueConstraints = @UniqueConstraint(columnNames = { "course_id", "order_index" }))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseOutline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

     @Column(name = "total_expected_sessions", nullable = false)
    private Integer totalExpectedSessions = 1;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentType contentType;

    @Column(name = "content_url", length = 512)
    private String contentUrl;

    @Column(nullable = false)
    private Integer duration;

    public enum ContentType {
        PDF, VIDEO, LIVE, SLIDES
    }

    public CourseOutline(Long id) {
        this.id = id;
    }
}
