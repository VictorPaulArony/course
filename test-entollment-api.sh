#!/bin/bash

BASE_URL="http://localhost:8080/api/enrollments"

# 1. Enroll a student
echo "Enrolling a student..."
curl -s -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "courseId": 1,
    "paymentStatus": "PENDING",
    "paymentMethod": "PAYPAL"
  }' | jq .

# 2. Get enrollments by student
echo "Getting enrollments by student..."
curl -s -X GET "$BASE_URL/student/1" | jq .

# 3. Get enrollments by course
echo "Getting enrollments by course..."
curl -s -X GET "$BASE_URL/course/1" | jq .

# 4. Get specific enrollment
echo "Getting specific enrollment..."
curl -s -X GET "$BASE_URL/student/1/course/1" | jq .

# 5. Count completed enrollments for a course
echo "Counting completed enrollments for a course..."
curl -s -X GET "$BASE_URL/course/1/count" | jq .

# 6. Get enrollments by teacher
echo "Getting enrollments by teacher..."
curl -s -X GET "$BASE_URL/teacher/1" | jq .
