# Test API Endpoints for Course Management
This document provides a set of test cases for the Course Management API endpoints. Each test case includes a brief description, the HTTP method, endpoint, and example curl command to execute the request.


## Test Cases

## shell script 
To run these tests...

```bash
chmod +x test-course-api.sh
```
Run the script to execute all test cases:

```bash
./test-course-api.sh
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


