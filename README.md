
# Social API Server (Spring Boot - In-Memory)

This is a lightweight,  Java Spring Boot API server that supports basic social media functionality — like user signup, login (JWT-based), post creation, post liking, and post deletion — using in-memory storage (no database). Designed for demonstration, interview, or prototype purposes.

---

## 🚀 Features

- User Signup and Login with JWT Token generation
- JWT-based Authentication and Authorization
- Create Posts, Like Posts, Delete Posts
- List All Posts
- In-memory storage for users and posts
- Logging and error handling built-in
- Postman collection for easy API testing

---

## 🛠 Tech Stack

- Java 17+
- Spring Boot
- Spring Security (with custom JWT filter)
- In-memory `Map` storage (no database)
- Postman (for API testing)

---

## 📂 Project Structure

```
README.md                    # Setup and usage guide
postman_collection.json      # (Optional) Postman collection for API testing
```

---

## 🧪 API Endpoints

### 🔐 Auth

| Method | Endpoint            | Description           |
|--------|---------------------|-----------------------|
| POST   | `/api/auth/signup`  | Register new user     |
| POST   | `/api/auth/login`   | Login and get JWT     |
| POST   | `/api/auth/refresh` | renew access token based on refresh token    |

### 📝 Posts (Requires Bearer Token)

| Method | Endpoint                    | Description               |
|--------|-----------------------------|---------------------------|
| POST   | `/api/posts/create`         | Create a new post         |
| POST   | `/api/posts/like/{id}`      | Like a post               |
| DELETE | `/api/posts/delete/{id}`    | Delete your post          |
| GET    | `/api/posts`                | List all posts            |
| GET    | `/api/posts/{id}/like-info` | Get post like info        |

---

## ▶️ How to Run

1. Clone the repo:
```bash
git clone https://github.com/YOUR_USERNAME/social-api-server.git
cd social-api-server
```
---

## 🧪 How to Test with Postman

1. Open Postman
2. Import this collection:  
   👉 [[Postman Collection Link](https://postman.co/workspace/My-Workspace~a6554489-8f32-4bb1-840a-b778cdfd86c6/request/40281184-c2dec6b8-789b-4990-8de9-c3bcb9acde73)](https://.postman.co/workspace/My-Workspace~a6554489-8f32-4bb1-840a-b778cdfd86c6/collection/40281184-51d2ecf0-dfe7-47a8-b1ce-1d5056362c60?action=share&creator=40281184&active-environment=40281184-417c720a-c0ea-4995-8797-fa2b52e7fcde)
3. Follow the order:  
   - `Signup` → `Login` → copy `token`
   - Set token as `Bearer <token>` in Authorization tab for other requests

---

## 📌 Notes

- All user and post data is stored in-memory and will be lost on restart.
- JWT is base64-encoded (not secure for production use).
- Best suited for prototyping, testing, or interview showcases.

---

## 📄 License

This project is open-source and free to use.
