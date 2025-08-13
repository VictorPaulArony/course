package com.lms.course.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lms.course.model.Course;
import com.lms.course.model.CourseOutline;
import com.lms.course.model.CourseSession;

public class CourseSessionTest {

    private Course course;
    private CourseOutline courseOutline;

    @BeforeEach
    void setUp() {
        course = mock(Course.class);
        courseOutline = mock(CourseOutline.class);
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();

        CourseSession session = new CourseSession(
            1L,
            course,
            courseOutline,
            "Java Programming",
            now,
            24

        );
        assertEquals(1L, session.getId());
        assertEquals(course, session.getCourse());
        assertEquals(courseOutline, session.getOutline());
        assertEquals("Java Programming", session.getSessionName());
        assertEquals(now, session.getSessionDate());
        assertEquals(24, session.getDuration());
    }

    @Test
    void testSettersAndGetters() {
        CourseSession session = new CourseSession();

        session.setId(2L);
        session.setCourse(course);
        session.setOutline(courseOutline);
        session.setSessionName("Java");
        LocalDateTime timeStamp = LocalDateTime.now();
        session.setSessionDate(timeStamp);
        session.setDuration(42);


        assertEquals(session.getId(), 2L);
        assertEquals(session.getCourse(), course);
        assertEquals(session.getOutline(), courseOutline);
        assertEquals(session.getSessionName(), "Java");
        assertEquals(session.getSessionDate(), timeStamp);
        assertEquals(session.getDuration(), 42);
    }
    
}
