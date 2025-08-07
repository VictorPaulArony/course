CREATE TABLE IF NOT EXISTS student (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS teacher (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    duration INT NOT NULL, 
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    mode ENUM('ONLINE', 'OFFLINE', 'HYBRID') NOT NULL, -- Mode of delivery
    price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    teacher_id BIGINT, -- ID of the teacher who created the course
    Payment_method ENUM('MPESA', 'CREDIT_CARD', 'PAYPAL', 'BANK_TRANSFER') DEFAULT 'CREDIT_CARD', -- Payment account for the course
    payment_account VARCHAR(255), -- Details of the payment account (e.g., MPESA number, credit card details)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS course_outline (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    order_index INT NOT NULL,
    total_expected_sessions INT DEFAULT 1 NOT NULL,
    content_type ENUM('PDF', 'VIDEO', 'LIVE', 'SLIDES') NOT NULL, 
    content_url VARCHAR(255), -- URL for the content (e.g., video, PDF)
    duration INT NOT NULL, -- Duration in minutes for v-- Type of content (PDF, Video, Text, Quiz)ideos or live sessions
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS course_enrollment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_status ENUM('PENDING', 'PARTIALLY_PAID', 'COMPLETED', 'FAILED') DEFAULT 'PENDING',
    payment_method ENUM('MPESA','CREDIT_CARD', 'PAYPAL', 'BANK_TRANSFER') DEFAULT 'CREDIT_CARD',
    amount_due DECIMAL(10, 2) DEFAULT 0.00,
    amount_paid DECIMAL(10, 2) DEFAULT 0.00,
    UNIQUE (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS course_session (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    outline_id BIGINT NOT NULL,
    session_name VARCHAR(255) NOT NULL,
    session_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    duration INT NOT NULL, -- Duration in minutes
    FOREIGN KEY (outline_id) REFERENCES course_outline(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);

-- This table tracks a student's attendance for each *specific scheduled course session*.
CREATE TABLE IF NOT EXISTS student_attendance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    course_session_id BIGINT NOT NULL, -- Refers to a specific scheduled session from course_session
    attendance_status ENUM('PRESENT', 'ABSENT', 'EXCUSED') NOT NULL,
    marked_by ENUM('TEACHER', 'SYSTEM') NOT NULL, -- Indicates if attendance was marked by a teacher or automatically by the system
    marked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- When the attendance was recorded
    UNIQUE (student_id, course_session_id), -- Ensures a student has only one attendance record per scheduled session
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (course_session_id) REFERENCES course_session(id) ON DELETE CASCADE
);

-- This table tracks the overall progress for a student *per course outline*.
-- The progress is calculated based on the student's attendance in the sessions linked to that outline.
CREATE TABLE IF NOT EXISTS course_progress (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    outline_id BIGINT NOT NULL, -- Refers to a specific topic/unit from course_outline
    course_id BIGINT NOT NULL, -- Redundant but useful for direct queries and joins
    completed_sessions_count INT DEFAULT 0 NOT NULL, -- Number of sessions a student has attended for this outline
    progress_percentage DECIMAL(5,2) DEFAULT 0.00 NOT NULL, -- Calculated percentage of sessions completed for this outline
    status ENUM('COMPLETED', 'IN_PROGRESS', 'NOT_STARTED') DEFAULT 'NOT_STARTED',
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Automatically updates on row modification
    UNIQUE (student_id, outline_id), -- Ensures a student has only one progress entry per outline
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (outline_id) REFERENCES course_outline(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);

--mock fo testing 


INSERT INTO student (id, username, lastname, password)
VALUES (1, 'john_doe', 'Doe', 'securepassword');

INSERT INTO teacher (id, username, password)
VALUES (1, 'Dr. Smith', '1234qwer');

INSERT INTO course (
    id, title, description, duration, start_date, end_date,
    mode, price, teacher_id, payment_method, payment_account
) VALUES (
    1, 'Java Basics', 'Intro to Java', 30,
    '2025-08-01 09:00:00', '2025-08-30 18:00:00',
    'ONLINE', 500.00, 1, 'MPESA', '0712345678'
);

INSERT INTO course_outline (
    id, course_id, title, description, order_index,
    total_expected_sessions, content_type, content_url, duration
) VALUES (
    1, 1, 'Java Introduction', 'Getting started with Java', 1,
    3, 'VIDEO', 'https://example.com/java-intro', 45
);

INSERT INTO course_enrollment (
    student_id, course_id, payment_status, payment_method,
    amount_due, amount_paid
) VALUES (
    1, 1, 'COMPLETED', 'MPESA', 500.00, 500.00
);


