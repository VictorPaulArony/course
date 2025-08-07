package com.lms.course.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Use Spring's Transactional

import com.lms.course.dto.CourseSessionDTO;
import com.lms.course.dto.CourseProgressDTOs;
import com.lms.course.dto.StudentAttendanceDTO;
import com.lms.course.model.Course;
import com.lms.course.model.CourseOutline;
import com.lms.course.model.CourseProgress;
import com.lms.course.model.CourseSession;
import com.lms.course.model.Student;
import com.lms.course.model.StudentAttendance;
import com.lms.course.repository.CourseOutlineRepository;
import com.lms.course.repository.CourseProgressRepository;
import com.lms.course.repository.CourseRepository;
import com.lms.course.repository.CourseSessionRepository;
import com.lms.course.repository.StudentAttendanceRepository; 
import com.lms.course.repository.StudentRepository; 

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j; // For logging

@Service
@AllArgsConstructor 
@NoArgsConstructor 
@Transactional 
@Slf4j // Lombok for logger
public class CourseProgressService {

    // Repositories for interacting with the database
    @Autowired
    private CourseSessionRepository courseSessionRepository;
    @Autowired
    private CourseProgressRepository courseProgressRepository;
    @Autowired
    private StudentAttendanceRepository studentAttendanceRepository;
    @Autowired
    private StudentRepository studentRepository; 
    @Autowired
    private CourseRepository courseRepository; 
    @Autowired
    private CourseOutlineRepository courseOutlineRepository; 

    
    public CourseSessionDTO createScheduledSession(CourseSessionDTO dto) {
        // Fetch related entities to ensure they exist
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + dto.getCourseId()));
        CourseOutline outline = courseOutlineRepository.findById(dto.getOutlineId())
                .orElseThrow(() -> new IllegalArgumentException("Course Outline not found with ID: " + dto.getOutlineId()));

        CourseSession session = new CourseSession();
        session.setCourse(course);
        session.setOutline(outline);
        session.setSessionName(dto.getSessionName());
        session.setSessionDate(dto.getSessionDate());
        session.setDuration(dto.getDuration());

        CourseSession savedSession = courseSessionRepository.save(session);
        log.info("Created scheduled session: {}", savedSession.getId());
        return mapCourseSessionToDTO(savedSession);
    }

    /**
     * Marks attendance for a student for a specific scheduled course session.
     * This method can be called manually by a teacher or dynamically by a system.
     * After marking attendance, it triggers an update to the student's CourseProgress.
     *
     * @param dto The StudentAttendanceDTO containing attendance details.
     * @return The created StudentAttendanceDTO.
     */
    public StudentAttendanceDTO markAttendance(StudentAttendanceDTO dto) {
        // Fetch related entities
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + dto.getStudentId()));
        CourseSession courseSession = courseSessionRepository.findById(dto.getCourseSessionId())
                .orElseThrow(() -> new IllegalArgumentException("Course Session not found with ID: " + dto.getCourseSessionId()));

        // Check if attendance already exists for this student and session
        Optional<StudentAttendance> existingAttendance = studentAttendanceRepository
                .findByStudentAndCourseSession(student, courseSession);

        StudentAttendance attendance;
        if (existingAttendance.isPresent()) {
            attendance = existingAttendance.get();
            // Update existing attendance if needed (e.g., status change)
            attendance.setAttendanceStatus(StudentAttendance.AttendanceStatus.valueOf(dto.getAttendanceStatus()));
            attendance.setMarkedBy(StudentAttendance.MarkedBy.valueOf(dto.getMarkedBy()));
            log.warn("Updating existing attendance record for student {} in session {}.", student.getId(), courseSession.getId());
        } else {
            // Create new attendance record
            attendance = new StudentAttendance();
            attendance.setStudent(student);
            attendance.setCourseSession(courseSession);
            attendance.setAttendanceStatus(StudentAttendance.AttendanceStatus.valueOf(dto.getAttendanceStatus()));
            attendance.setMarkedBy(StudentAttendance.MarkedBy.valueOf(dto.getMarkedBy()));
            log.info("Creating new attendance record for student {} in session {}.", student.getId(), courseSession.getId());
        }

        StudentAttendance savedAttendance = studentAttendanceRepository.save(attendance);

        // After marking attendance, update the student's course progress for the relevant outline
        updateCourseProgressForOutline(student, courseSession.getOutline());

        return mapStudentAttendanceToDTO(savedAttendance);
    }

    /**
     * Updates a student's CourseProgress for a specific CourseOutline.
     * This method is called automatically after attendance is marked.
     * It recalculates the completed sessions count and progress percentage for that outline.
     *
     * @param student The student whose progress needs to be updated.
     * @param outline The course outline for which progress needs to be updated.
     */
    private void updateCourseProgressForOutline(Student student, CourseOutline outline) {
        // Find existing CourseProgress or create a new one
        CourseProgress courseProgress = courseProgressRepository
                .findByStudentAndOutline(student, outline)
                .orElseGet(() -> {
                    CourseProgress newProgress = new CourseProgress();
                    newProgress.setStudent(student);
                    newProgress.setOutline(outline);
                    newProgress.setCourse(outline.getCourse()); // Link to the course via outline
                    newProgress.setStatus(CourseProgress.Status.NOT_STARTED);
                    return newProgress;
                });

        // Count completed sessions for this student and outline
        // A session is considered 'completed' if the student was PRESENT for it.
        Long completedCount = studentAttendanceRepository.countByStudentAndCourseSession_OutlineAndAttendanceStatus(
                student, outline, StudentAttendance.AttendanceStatus.PRESENT);

        courseProgress.setCompletedSessionsCount(completedCount.intValue());

        // Get the total expected sessions for this outline
        Integer totalExpectedSessions = outline.getTotalExpectedSessions();
        if (totalExpectedSessions == null || totalExpectedSessions <= 0) {
            log.warn("CourseOutline {} has invalid totalExpectedSessions: {}. Defaulting to 1.", outline.getId(), totalExpectedSessions);
            totalExpectedSessions = 1; // Prevent division by zero
        }

        // Calculate progress percentage
        double progressPercentage = (double) completedCount / totalExpectedSessions * 100.0;
        courseProgress.setProgressPercentage(Math.min(100.0, progressPercentage)); // Cap at 100%

        // Determine overall status for the outline
        if (courseProgress.getCompletedSessionsCount() >= totalExpectedSessions) {
            courseProgress.setStatus(CourseProgress.Status.COMPLETED);
        } else if (courseProgress.getCompletedSessionsCount() > 0) {
            courseProgress.setStatus(CourseProgress.Status.IN_PROGRESS);
        } else {
            courseProgress.setStatus(CourseProgress.Status.NOT_STARTED);
        }

        courseProgressRepository.save(courseProgress);
        log.info("Updated CourseProgress for student {} in outline {}: {}/{} sessions completed, {}% progress, Status: {}",
                student.getId(), outline.getId(), completedCount, totalExpectedSessions,
                String.format("%.2f", courseProgress.getProgressPercentage()), courseProgress.getStatus());
    }

    /**
     * Retrieves a student's progress for a specific course outline.
     *
     * @param studentId The ID of the student.
     * @param outlineId The ID of the course outline.
     * @return CourseProgressDTO for the specified student and outline, or null if not found.
     */
    public CourseProgressDTOs getStudentProgressForOutline(Long studentId, Long outlineId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
        CourseOutline outline = courseOutlineRepository.findById(outlineId)
                .orElseThrow(() -> new IllegalArgumentException("Course Outline not found with ID: " + outlineId));

        Optional<CourseProgress> progress = courseProgressRepository.findByStudentAndOutline(student, outline);
        return progress.map(this::mapCourseProgressToDTO).orElse(null);
    }

    /**
     * Retrieves the overall progress for a student across all outlines in a specific course.
     * This is an aggregation of individual outline progress.
     *
     * @param studentId The ID of the student.
     * @param courseId The ID of the course.
     * @return A calculated overall progress percentage for the course.
     */
    public Double getOverallCourseProgress(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));

        // Get all outlines for the course
        long totalOutlinesInCourse = courseOutlineRepository.countByCourse(course);
        if (totalOutlinesInCourse == 0) {
            return 0.0; // No outlines, no progress
        }

        // Sum up the progress percentages for each outline for this student
        // This approach assumes each outline contributes equally to overall course progress.
        // A more complex weighting could be implemented if needed.
        Double sumOfOutlineProgressPercentages = courseProgressRepository
                .findProgressPercentagesByStudentAndCourse(student, course)
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        // Calculate average progress across all outlines
        double overallProgress = sumOfOutlineProgressPercentages / totalOutlinesInCourse;

        log.info("Overall progress for student {} in course {}: {}%", studentId, courseId, String.format("%.2f", overallProgress));
        return Math.min(100.0, overallProgress); // Cap at 100%
    }


    // --- Mapper Methods ---

    private CourseSessionDTO mapCourseSessionToDTO(CourseSession session) {
        return CourseSessionDTO.builder()
                .id(session.getId())
                .courseId(session.getCourse().getId())
                .outlineId(session.getOutline().getId())
                .sessionName(session.getSessionName())
                .sessionDate(session.getSessionDate())
                .duration(session.getDuration())
                .build();
    }

    private StudentAttendanceDTO mapStudentAttendanceToDTO(StudentAttendance attendance) {
        return StudentAttendanceDTO.builder()
                .id(attendance.getId())
                .studentId(attendance.getStudent().getId())
                .courseSessionId(attendance.getCourseSession().getId())
                .attendanceStatus(attendance.getAttendanceStatus().name())
                .markedBy(attendance.getMarkedBy().name())
                .build();
    }

    private CourseProgressDTOs mapCourseProgressToDTO(CourseProgress progress) {
        return CourseProgressDTOs.builder()
                .id(progress.getId())
                .studentId(progress.getStudent().getId())
                .outlineId(progress.getOutline().getId())
                .courseId(progress.getCourse().getId())
                .outlineTitle(progress.getOutline().getTitle()) // Include outline title
                .completedSessionsCount(progress.getCompletedSessionsCount())
                .totalExpectedSessions(progress.getOutline().getTotalExpectedSessions()) // Include total expected
                .progressPercentage(progress.getProgressPercentage())
                .status(progress.getStatus().name())
                .lastUpdated(progress.getLastUpdated())
                .build();
    }
}