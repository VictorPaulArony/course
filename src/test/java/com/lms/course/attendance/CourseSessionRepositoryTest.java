package com.lms.course.attendance;

import com.lms.course.model.CourseOutline;
import com.lms.course.model.CourseSession;
import com.lms.course.repository.CourseSessionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CourseSessionRepositoryTest {

    private CourseSessionRepository repository;

    private CourseOutline outline;

    @BeforeEach
    void setUp() {
        repository = mock(CourseSessionRepository.class);
        outline = new CourseOutline(); 
    }

    @Test
    void testFindByOutline() {
        CourseSession session1 = new CourseSession();
        CourseSession session2 = new CourseSession();
        List<CourseSession> expectedSessions = Arrays.asList(session1, session2);

        when(repository.findByOutline(outline)).thenReturn(expectedSessions);

        List<CourseSession> result = repository.findByOutline(outline);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedSessions, result);
        verify(repository, times(1)).findByOutline(outline);
    }
}

