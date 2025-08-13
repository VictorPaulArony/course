package com.lms.course.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lms.course.model.Course;
import com.lms.course.model.CourseOutline;
import com.lms.course.model.CourseProgress;
import com.lms.course.model.Student;

public class CourseProgressTest {

    private Student student;
    private CourseOutline courseOutline;
    private Course course;


    @BeforeEach
    void setUP() {
        course = mock(Course.class);
        student = mock(Student.class);
        courseOutline = mock(CourseOutline.class);
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime timeNow = LocalDateTime.now(); 
        CourseProgress progress = new CourseProgress(
                1L,
                student,
                courseOutline,
                course,
                12,
                50.0,
                CourseProgress.Status.IN_PROGRESS,
                timeNow
        );

        assertEquals(1L, progress.getId());
        assertEquals(student, progress.getStudent());
        assertEquals(courseOutline, progress.getOutline());
        assertEquals(course, progress.getCourse());
        assertEquals(12, progress.getCompletedSessionsCount());
        assertEquals(50.0, progress.getProgressPercentage());
        assertEquals(CourseProgress.Status.IN_PROGRESS, progress.getStatus());
        assertEquals(timeNow, progress.getLastUpdated());

    }

    @Test
    void testSettersAndGetters() {
        CourseProgress progress = new CourseProgress();

        progress.setId(2L);
        progress.setStudent(student);
        progress.setOutline(courseOutline);
        progress.setCourse(course);
        progress.setCompletedSessionsCount(12);
        progress.setProgressPercentage(50.0);
        progress.setStatus(CourseProgress.Status.IN_PROGRESS);
        LocalDateTime timeNow = LocalDateTime.now();
        progress.setLastUpdated(timeNow);


        assertEquals(progress.getId(), 2L);
        assertEquals(progress.getStudent(), student);
        assertEquals(progress.getOutline(), courseOutline);
        assertEquals(progress.getCourse(), course);
        assertEquals(progress.getCompletedSessionsCount(), 12);
        assertEquals(progress.getStatus(), CourseProgress.Status.IN_PROGRESS);
        assertEquals(progress.getProgressPercentage(), 50.0);
        assertEquals(progress.getLastUpdated(), timeNow);
    }

    @Test
    void testEnumValues() {
        assertEquals(CourseProgress.Status.COMPLETED.name(), "COMPLETED");
        assertEquals(CourseProgress.Status.IN_PROGRESS.name(), "IN_PROGRESS" );
        assertEquals(CourseProgress.Status.NOT_STARTED.name(), "NOT_STARTED");
    }

}
