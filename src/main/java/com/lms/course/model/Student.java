package com.lms.course.model;

import jakarta.persistence.*;
import lombok.*;

//mockdata set for the students
@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // other student details
}
