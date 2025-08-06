package com.lms.course.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.course.dto.CourseSessionDTO;
import com.lms.course.model.CourseSession;
import com.lms.course.repository.CourseOutlineRepository;
import com.lms.course.repository.CourseRepository;
import com.lms.course.repository.CourseSessionRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class CourseProgressService {

    @Autowired
    private CourseSessionRepository courseSessionRepository;

    // @Autowired
    // private CourseProgressRepository courseProgressRepository;

    // @Autowired
    // private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseOutlineRepository courseOutlineRepository;

    //CREATE
    public CourseSessionDTO createSession(CourseSessionDTO dto) {
        CourseSession session = new CourseSession();
        // session.setStudent(student);
        session.setCourseId(courseRepository.getReferenceById(dto.getCourseId()));
        session.setOutlineId(courseOutlineRepository.getReferenceById(dto.getOutlineId()));
        session.setSessionDate(
                dto.getSessionDate() != null
                        ? dto.getSessionDate()
                        : LocalDateTime.now());
        session.setDuration(dto.getDuration());
        session.setStatus(
                CourseSession.Status.valueOf(dto.getStatus()) != null
                        ? CourseSession.Status.valueOf(dto.getStatus())
                        : CourseSession.Status.NOT_STARTED);
        CourseSession savedSession = courseSessionRepository.save(session);
        return mapToDTO(savedSession);
    }

    //UPDATE


    
    private CourseSessionDTO mapToDTO(CourseSession session) {
        CourseSessionDTO dto = new CourseSessionDTO();
        dto.setId(session.getId());
        dto.setStudentId(session.getStudentId());
        dto.setCourseId(session.getCourseId().getId());
        dto.setOutlineId(session.getOutlineId().getId());
        dto.setSessionDate(session.getSessionDate());
        dto.setDuration(session.getDuration());
        dto.setStatus(session.getStatus().name());
        // dto.setAttendanceMarked(session.getAttendanceMarked());
        // dto.setAttendanceStatus(session.getAttendanceStatus());
        return dto;
    }
}
