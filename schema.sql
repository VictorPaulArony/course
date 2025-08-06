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
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    outline_id BIGINT NOT NULL, -- ID of the course outline this session belongs to
    session_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    duration INT NOT NULL, -- Duration in minutes
    status ENUM('COMPLETED', 'STARTED', 'NOT_STARTED') DEFAULT 'NOT_STARTED', 
    UNIQUE (student_id, outline_id), -- Ensure a student can only have one session per outline
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (outline_id) REFERENCES outline(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);

--Represents a scheduled session for a given outline (e.g., "Week 1 - Basics").
CREATE TABLE course_outline_session (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    outline_id BIGINT NOT NULL,
    session_date TIMESTAMP NOT NULL,
    duration INT NOT NULL,
    FOREIGN KEY (outline_id) REFERENCES course_outline(id) ON DELETE CASCADE
);

--Tracks whether a student attended a particular CourseOutlineSession.
CREATE TABLE student_session_attendance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    session_id BIGINT NOT NULL,
    status ENUM('PRESENT', 'ABSENT', 'NOT_MARKED') DEFAULT 'NOT_MARKED',
    marked_by_teacher BOOLEAN DEFAULT FALSE,
    UNIQUE (student_id, session_id),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (session_id) REFERENCES course_outline_session(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS course_progress (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    outline_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    status ENUM('COMPLETED', 'IN_PROGRESS', 'NOT_STARTED') DEFAULT 'NOT_STARTED', 
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (student_id, outline_id ), -- Ensure a student can only have one progress entry per outline
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
    FOREIGN KEY (outline_id) REFERENCES course_outline(id) ON DELETE CASCADE
);
