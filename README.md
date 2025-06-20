
# Social API Server (Spring Boot - In-Memory)

This is a lightweight,  Java Spring Boot API server that supports basic social media functionality — like user signup, login (JWT-based), post creation, post liking, and post deletion — using in-memory storage (no database). Designed for demonstration, interview, or prototype purposes.

---

## 🚀 Features

- User Signup and Login with JWT Token generation
- JWT-based Authentication and Authorization
- Create Posts, Like Posts, Delete Posts, Lists all posts by user, get posts by how much user liked
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
social-api-server/
├── src/
│   └── main/
│       └── java/
│           └── org/
│               └── bhavesh/
│                   └── socialapiserver/
│                       ├── controller/
│                       │   ├── AuthController.java  //signup , login
│                       │   └── PostController.java  // all post apis 
│                       │
                        ├── config/
│                       │   ├──AppConfig.java  //for custom beans
│               

│                       ├── model/
│                       │   ├── User.java   // pojo classes
│                       │   └── Post.java
│                       │
                        ├── dto/
│                       │   ├── ApiResponse.java   
│                       │   └── LoginRequest.java
                        │   ├── LoginResponse.java
                        │   ├── PostRequest.java
                        │   ├── SignupRequest.java  

│                       ├── storage/
│                       │   ├── UserStorage.java     // In-memory storage for users
│                       │   └── PostStorage.java     // In-memory storage for posts
│                       │
│                       ├── security/
│                       │   ├── JwtFilter.java       // @Component
│                       │   ├── JwtUtil.java
│                       │   └── SecurityConfig.java  // Spring Security Config
│                       │
│                       ├── exception/
│                       │   └── UserException.java
│                       │   └── PostException.java
│                       │
│                       └── SocialApiServerApplication.java
│
├── .gitignore
├── README.md
├── pom.xml


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
| GET    | `/api/posts/by-user/{username }| Get post by user       |

---

## ▶️ How to Run

1. Clone the repo:
```bash
git clone https://github.com/YOUR_USERNAME/social-api-server.git
cd social-api-server
```mvn clean install
``` Run Springboot app
---

## 🧪 How to Test with Postman

1. Open Postman
2. Import this collection:  
   👉## 🔄 Postman Collection (Local Import)

A ready-to-use Postman collection is included in this project for testing all APIs.

📁 File:  
`postman/social_api_server.postman_collection.json`

### 🚀 How to Use

1. Open Postman
2. Click **Import**
3. Select the file: `social_api_server.postman_collection.json`
4. You’ll now see all endpoints organized under the `social_api_server` collection
   
   - `Signup` → `Login` → Refresh(if access token expired)

    `Post Create` → `Post Like` →  `List Posts` → `get Posts by Likes` →  `get Posts by User` → `Delete Post`
   - we have environment variables in postman collection
   - base_url - http://localhost:8080
   
   - accessToken - initially empty  ,
   - 
   - postId - initially empty

  - after /api/auth/login response access token automatically sets in bearer token of other request
  - after  `/api/posts/create` response postId automatically sets in environment variable which is used on other request

   PS -> if you hit `/api/posts/delete/{id}` request you need to hit `/api/posts/create` request again for other api's to work
    in case of  single post, if multiple post then set postId manually from list of posts to postId env variable.


---

## 📌 Notes

- All user and post data is stored in-memory and will be lost on restart.
- JWT is base64-encoded (not secure for production use).
- Best suited for prototyping, testing, or interview showcases.

---

## 📄 License

This project is open-source and free to use.
