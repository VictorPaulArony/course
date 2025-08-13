package com.lms.course.attendance;
import com.lms.course.model.Course;
import com.lms.course.model.CourseOutline;
import com.lms.course.model.CourseProgress;
import com.lms.course.model.Student;
import com.lms.course.repository.CourseProgressRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CourseProgressRepositoryTest {

    private CourseProgressRepository repository;

    private Student student;
    private CourseOutline outline;
    private Course course;

    @BeforeEach
    void setUp() {
        repository = mock(CourseProgressRepository.class);
        student = new Student();
        outline = new CourseOutline();
        course = new Course();
    }

    @Test
    void testFindByStudentAndOutline() {
        CourseProgress progress = new CourseProgress();
        when(repository.findByStudentAndOutline(student, outline))
                .thenReturn(Optional.of(progress));

        Optional<CourseProgress> result = repository.findByStudentAndOutline(student, outline);

        assertTrue(result.isPresent());
        assertEquals(progress, result.get());
        verify(repository, times(1)).findByStudentAndOutline(student, outline);
    }

    @Test
    void testExistsByStudentIdAndOutlineId() {
        when(repository.existsByStudentIdAndOutlineId(1L, 2L)).thenReturn(true);

        boolean exists = repository.existsByStudentIdAndOutlineId(1L, 2L);

        assertTrue(exists);
        verify(repository, times(1)).existsByStudentIdAndOutlineId(1L, 2L);
    }

    @Test
    void testFindProgressPercentagesByStudentAndCourse() {
        List<Double> expectedPercentages = Arrays.asList(75.0, 88.5, 100.0);
        when(repository.findProgressPercentagesByStudentAndCourse(student, course))
                .thenReturn(expectedPercentages);

        List<Double> result = repository.findProgressPercentagesByStudentAndCourse(student, course);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(expectedPercentages, result);
        verify(repository, times(1)).findProgressPercentagesByStudentAndCourse(student, course);
    }
}

