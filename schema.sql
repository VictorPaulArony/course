CREATE TABLE IF NOT EXISTS courses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    duration INT NOT NULL, -- Duration in weeks/months/years
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
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS course_enrollment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_status ENUM('PENDING', 'COMPLETED', 'FAILED') DEFAULT 'PENDING',
    payment_method ENUM('MPESA','CREDIT_CARD', 'PAYPAL', 'BANK_TRANSFER') DEFAULT 'CREDIT_CARD',
    UNIQUE (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);