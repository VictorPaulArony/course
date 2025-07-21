# Test API Endpoints for Course Management
This document provides a set of test cases for the Course Management API endpoints. Each test case includes a brief description, the HTTP method, endpoint, and example curl command to execute the request.


## Test Cases

## shell script 
To run these tests...

```bash
chmod +x test-course-api.sh
chmod +x test-course-outline.sh
chmod +x test-entollment-api.sh
```
Run the script to execute all test cases:

```bash
./test-course-api.sh
./test-course-outline.sh
./test-entollment-api.sh
```

### 🔸 1. Create a new course

```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Java Programming",
    "description": "A complete guide to Java SE",
    "duration": 90,
    "startDate": "2025-08-01T09:00:00",
    "endDate": "2025-11-01T17:00:00",
    "mode": "ONLINE",
    "price": 199.99,
    "teacherId": 1,
    "paymentMethod": "CREDIT_CARD",
    "paymentAccount": "4111-1111-1111-1111"
  }'
```
---

### 🔸 2. Get a course by ID

```bash
curl -X GET http://localhost:8080/api/courses/1
```
---

### 🔸 3. Get all courses

```bash
curl -X GET http://localhost:8080/api/courses
```

---

### 🔸 4. Update an existing course

```bash
curl -X PUT http://localhost:8080/api/courses/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Advanced Java Programming",
    "description": "Deep dive into Java and Spring",
    "duration": 120,
    "startDate": "2025-08-10T09:00:00",
    "endDate": "2025-12-10T17:00:00",
    "mode": "HYBRID",
    "price": 299.99,
    "teacherId": 1,
    "paymentMethod": "BANK_TRANSFER",
    "paymentAccount": "DE89-3704-0044-0532-0130-00"
  }'
```

---

### 🔸 5. Delete a course

```bash
curl -X DELETE http://localhost:8080/api/courses/1
```

---

# Course Enrollment API Endpoints


### 🔸 1. **Enroll a Student**

**Endpoint:** `POST /api/enrollments`

```bash
curl -X POST http://localhost:8080/api/enrollments \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "courseId": 1,
    "paymentStatus": "PENDING",
    "paymentMethod": "PAYPAL"
}'
```

> ✅ `paymentStatus` should be one of: `PENDING`, `PARTIALLY_PAID`, `COMPLETED`, `FAILED`
> ✅ `paymentMethod` should be one of: `CREDIT_CARD`, `PAYPAL`, `BANK_TRANSFER` (based on your naming assumption)

---

### 🔸 2. **Get Enrollments by Student ID**

**Endpoint:** `GET /api/enrollments/student/{studentId}`

```bash
curl http://localhost:8080/api/enrollments/student/1
```

---

### 🔸 3. **Get Enrollments by Course ID**

**Endpoint:** `GET /api/enrollments/course/{courseId}`

```bash
curl http://localhost:8080/api/enrollments/course/1
```

---

### 🔸 4. **Get Specific Enrollment (Student + Course)**

**Endpoint:** `GET /api/enrollments/student/{studentId}/course/{courseId}`

```bash
curl http://localhost:8080/api/enrollments/student/1/course/1
```

---

### 🔸 5. **Count Completed Enrollments for a Course**

**Endpoint:** `GET /api/enrollments/course/{courseId}/count`

```bash
curl http://localhost:8080/api/enrollments/course/1/count
```

---

### 🔸 6. **Get Enrollments by Teacher ID**

**Endpoint:** `GET /api/enrollments/teacher/{teacherId}`

```bash
curl http://localhost:8080/api/enrollments/teacher/1```


---

### 🔸 7. **Update Payment Status for an Enrollment**

**Endpoint:** `PUT /api/enrollments/{enrollmentId}`

```bash
curl -X PUT http://localhost:8080/api/enrollments/1 \
  -H "Content-Type: application/json" \
  -d '{
    "amountPaidNow": 199.99
}'
```

# Course Outline API Endpoints

## Create a Course Outline

```bash
curl -X POST http://localhost:8080/api/courses/1/outlines \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Introduction to Java",
    "description": "Basics of Java programming",
    "orderIndex": 1,
    "contentType": "VIDEO",
    "contentUrl": "https://zone01kisumu.com/videos/java-intro.mp4",
    "duration": 60
  }'
```

## Get all Course Outlines for a Course

```bash
curl -X GET http://localhost:8080/api/courses/1/outlines
```

## Get a specific Course Outline

```bash
curl -X GET http://localhost:8080/api/courses/1/outlines/1
```

## Update a Course Outline

```bash
curl -X PUT http://localhost:8080/api/courses/1/outlines/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Introduction to Java - Updated",
    "description": "Basics of Java programming - Updated",
    "orderIndex": 1,
    "contentType": "VIDEO",
    "contentUrl": "https://zone01kisumu.com/videos/java-intro-updated.mp4",
    "duration": 60
  }'
```

## Delete a Course Outline

```bash
curl -X DELETE http://localhost:8080/api/courses/1/outlines/1
``` 

## get total duration of a course

```bash
curl -X GET http://localhost:8080/api/courses/1/outlines/total-duration
``` 
