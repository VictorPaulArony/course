# JSON response

JSON response examples for the Course Management API endpoints. These examples illustrate the expected structure and data types for successful responses from the API.

---

### ✅ Example: `POST /api/courses` — Create Course

**Request:**

```json
{
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
}
```

**Response (HTTP 201 Created):**

```json
{
  "id": 1,
  "title": "Java Programming",
  "description": "A complete guide to Java SE",
  "duration": 90,
  "startDate": "2025-08-01T09:00:00",
  "endDate": "2025-11-01T17:00:00",
  "mode": "ONLINE",
  "price": 199.99,
  "teacherId": 1,
  "paymentMethod": "CREDIT_CARD",
  "paymentAccount": "4111-1111-1111-1111",
  "createdAt": "2025-07-19T14:33:50.123456"
}
```

---

### ✅ Example: `GET /api/courses` — Get All Courses

**Response (HTTP 200 OK):**

```json
[
  {
    "id": 1,
    "title": "Java Programming",
    "description": "A complete guide to Java SE",
    "duration": 90,
    "startDate": "2025-08-01T09:00:00",
    "endDate": "2025-11-01T17:00:00",
    "mode": "ONLINE",
    "price": 199.99,
    "teacherId": 1,
    "paymentMethod": "CREDIT_CARD",
    "paymentAccount": "4111-1111-1111-1111",
    "createdAt": "2025-07-19T14:33:50.123456"
  },
  {
    "id": 2,
    "title": "Spring Boot Masterclass",
    "description": "Build real-world Spring apps",
    "duration": 60,
    "startDate": "2025-09-01T10:00:00",
    "endDate": "2025-10-15T16:00:00",
    "mode": "HYBRID",
    "price": 249.00,
    "teacherId": 2,
    "paymentMethod": "BANK_TRANSFER",
    "paymentAccount": "DE89-3704-0044-0532-0130-00",
    "createdAt": "2025-07-19T14:35:12.987654"
  }
]
```

---

### ✅ Example: `GET /api/courses/1` — Get Course by ID

**Response (HTTP 200 OK):**

```json
{
  "id": 1,
  "title": "Java Programming",
  "description": "A complete guide to Java SE",
  "duration": 90,
  "startDate": "2025-08-01T09:00:00",
  "endDate": "2025-11-01T17:00:00",
  "mode": "ONLINE",
  "price": 199.99,
  "teacherId": 1,
  "paymentMethod": "CREDIT_CARD",
  "paymentAccount": "4111-1111-1111-1111",
  "createdAt": "2025-07-19T14:33:50.123456"
}
```
