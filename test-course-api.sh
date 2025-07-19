#!/bin/bash

BASE_URL="http://localhost:8080/api/courses"

# 1. Create a new course
echo "📦 Creating a new course..."
curl -s -X POST "$BASE_URL" \
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
  }' | jq .

# 2. Get all courses
echo -e "\n📚 Getting all courses..."
curl -s -X GET "$BASE_URL" | jq .

# 3. Get a course by ID (ID=1)
echo -e "\n🔍 Getting course with ID 1..."
curl -s -X GET "$BASE_URL/1" | jq .

# 4. Update course with ID=1
echo -e "\n✏️ Updating course with ID 1..."
curl -s -X PUT "$BASE_URL/1" \
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
  }' | jq .

# 5. Delete course with ID=1
echo -e "\n🗑️ Deleting course with ID 1..."
curl -s -X DELETE "$BASE_URL/1" -w "\nStatus: %{http_code}\n"
