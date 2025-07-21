
#!/bin/bash

BASE_URL="http://localhost:8080/api/courses/1/outlines"

# 1. Create a new course outline
echo "📦 Creating a new course outline..."
curl -s -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Introduction to Java",
    "description": "Basics of Java programming",
    "orderIndex": 1,
    "contentType": "VIDEO",
    "contentUrl": "https://zone01kisumu.com/videos/java-intro.mp4",
    "duration": 60
  }' | jq .

# 2. Get all course outlines for a course
echo -e "\n📚 Getting all course outlines..."
curl -s -X GET "$BASE_URL" | jq .

# 3. Get a specific course outline by ID (ID=1)
echo -e "\n🔍 Getting specific course outline..."
curl -s -X GET "$BASE_URL/1" | jq .

# 4. Update a course outline with ID=1
echo -e "\n✏️ Updating course outline with ID 1..."
curl -s -X PUT "$BASE_URL/1" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Introduction to Java - Updated",
    "description": "Basics of Java programming - Updated",
    "orderIndex": 1,
    "contentType": "VIDEO",
    "contentUrl": "https://zone01kisumu.com/videos/java-intro-updated.mp4",
    "duration": 60
  }' | jq .

# 5. Delete a course outline with ID=1
echo -e "\n❌ Deleting course outline with ID 1..."
curl -s -X DELETE "$BASE_URL/1" -w "\nStatus: %{http_code}\n"

#verfiy deletion
echo -e "\n🔍 Verifying deletion..."
curl -s -X GET "$BASE_URL/1" -w "\nStatus: %{http_code}\n"