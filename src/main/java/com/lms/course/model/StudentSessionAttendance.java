package com.lms.course.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "student_session_attendance", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "student_id", "session_id" })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSessionAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private CourseOutlineSession session;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status = AttendanceStatus.NOT_MARKED;

    @Column(name = "marked_by_teacher")
    private Boolean markedByTeacher = false;

    public enum AttendanceStatus {
        PRESENT,
        ABSENT,
        NOT_MARKED
    }
}

