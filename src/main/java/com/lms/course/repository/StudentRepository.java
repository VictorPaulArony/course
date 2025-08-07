package com.lms.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.course.model.Student;

public interface  StudentRepository extends JpaRepository<Student, Long> {
    
    
}
