# ✅ API Summary

| Method | Endpoint            | Description      |
| ------ | ------------------- | ---------------- |
| POST   | `/api/courses`      | Create course    |
| GET    | `/api/courses/{id}` | Get course by ID |
| GET    | `/api/courses`      | List all courses |
| PUT    | `/api/courses/{id}` | Update course    |
| DELETE | `/api/courses/{id}` | Delete course    |

| Method | Endpoint                                          | Description                        |
| ------ | ------------------------------------------------- | ---------------------------------- |
| POST   | `/api/courses/{courseId}/outlines`                | Create new course outline          |
| GET    | `/api/courses/{courseId}/outlines`                | List outlines for course           |
| GET    | `/api/courses/{courseId}/outlines/{outlineId}`    | Get specific outline               |
| PUT    | `/api/courses/{courseId}/outlines/{outlineId}`    | Update outline                     |
| DELETE | `/api/courses/{courseId}/outlines/{outlineId}`    | Delete outline                     |
| GET    | `/api/courses/{courseId}/outlines/total-duration` | Get total duration of all outlines |


| Method | Endpoint                                      | Description                           |
| ------ | --------------------------------------------- | ------------------------------------- |
| POST   | `/api/enrollments`                            | Enroll a student                      |
| GET    | `/api/enrollments/student/{studentId}`        | Get enrollments by student            |
| GET    | `/api/enrollments/course/{courseId}`          | Get enrollments by course             |
| GET    | `/api/enrollments/student/{sId}/course/{cId}` | Get specific enrollment               |
| GET    | `/api/enrollments/course/{courseId}/count`    | Count completed enrollments in course |
| GET    | `/api/enrollments/teacher/{teacherId}`        | Get enrollments for teacher's courses |


