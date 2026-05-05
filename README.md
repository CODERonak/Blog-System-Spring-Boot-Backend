# 🚀 Deployed Project

**FOR NOW LINK IS UNAVAILABLE**

The project has been deployed and is available for testing.

*   **Deployed Link:** [https://blog-service-728516377302.us-central1.run.app](https://blog-service-728516377302.us-central1.run.app)
*   **Postman Collection:** [https://www.postman.com/flight-technologist-23919603/mono/request/42910295-c0e683d6-78a0-474d-b0dc-18ee5e18bbac?tab=body](https://www.postman.com/flight-technologist-23919603/mono/request/42910295-c0e683d6-78a0-474d-b0dc-18ee5e18bbac?tab=body)

### 🔑 API Usage

1.  **Generate a Token:** Use the "register" or "login" endpoint to get an authentication token.
2.  **Authorization:** In Postman, go to the "Authorization" tab and select "Bearer Token". Paste the generated token in the "Token" field.
3.  **Access Protected Endpoints:** You can now use this token to access the "profile" and "posts" endpoints.

**CAUTION:** Do not use your real username, email, or password. Use dummy data for testing.

### 📝 JSON Examples

#### Register

```json
{
  "username": "sam_reader",
  "password": "UserPass456",
  "email": "sam.jones@webmail.com",
  "role": "AUTHOR"
}
```

#### Login

Use the `username` and `password` from the registration to log in.

#### Profile

```json
{
  "name": "Sam Reader",
  "bio": "I love reading books and articles on a wide range of topics, from history and philosophy to the latest trends in technology."
}
```

#### Posts

```json
{
    "title": "Mastering CSS Grid Layout",
    "content": "CSS Grid Layout is a powerful two-dimensional layout system that allows you to create complex and responsive web layouts with ease. This tutorial will take you from the basics of creating a grid container to advanced topics like grid template areas, aligning items, and creating responsive designs with media queries. We'''ll build a practical example of a photo gallery to solidify your understanding. Say goodbye to float hacks and complex positioning!",
    "status": "PUBLISHED"
  }
```

# 📝 Spring Boot Blog System Backend API

A **Blog / Content Platform backend** built with **Spring Boot**, designed as a **Modular Monolith** with **strict domain isolation**, **SOLID principles**, and **Clean Architecture**.

The system is designed for **clear domain boundaries**, and **future microservice extraction**, while remaining a **single deployable application** on **Google Cloud Platform (GCP)**.

---

## ✨ Core Features & Technology

*   **Authentication**: Secure user access with **JWT (JSON Web Token)** based authentication.
*   **Deployment**: Ready for production on **Google Cloud Platform (GCP)**.
*   **Database**: Utilizes **Cloud SQL** for a managed, relational database service.
*   **File Storage**: Leverages **Google Cloud Storage** for scalable and secure file handling.

---

## 🎯 Project Goals

* Enforce **strong modular boundaries** inside a monolith
* Apply **SOLID principles** consistently
* Ensure **correctness under concurrent access**
* Remain deployable as **one JAR**
* Be **microservice-extractable later**

---

## 🧱 Architectural Philosophy — Modular Monolith

### What Modular Monolith Means Here

* Single deployable application (**one JAR**)
* Strong internal module boundaries
* Each module owns **exactly one business domain**
* **No shared database tables** across modules
* Inter-module communication **only via interfaces**
* No entity, repository, or transaction sharing

> This is **not** a package-based monolith.
> This is a **domain-isolated system**.

---

## 🚫 Hard Architectural Rules (Non-Negotiable)

* A module **cannot** access another module’s repository
* A module **cannot** access another module’s entities
* Cross-module interaction **only via public service interfaces**
* Controllers talk **only** to their own module
* DTOs never leak entities
* Infrastructure code never leaks into domain logic

---

## 🛠 Tech Stack

* **Java 21+**
* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **Spring Security**
* **MySQL (Cloud SQL)**
* **BCrypt**
* **Spring Validation**
* **Spring Actuator**
* **Docker / Docker Compose**
* **Google Cloud Storage**

---

## 🗂️ High-Level Module Map (Corrected)

```
com.example.blog
│
├── auth          (authentication & identity)
├── profile       (user profile data)
├── posts         (posts & publishing)
├── storage       (GCP file storage)
└── BlogApplication.java
```

✔ No shared kernel
✔ All modules are isolated
✔ Communication via interfaces only

---

## 🧱 Internal Module Structure (Standardized)

Each module follows the same internal structure:

```
module-name/
├── controller    (REST endpoints)
├── service
│   ├── interfaces
│   └── impl
├── repository
├── model
│   ├── entity
│   └── enums
├── mapper
└── exception
```

This structure is **mandatory and consistent**.

---

## 👥 User Roles (Unchanged)

* `ADMIN`
* `AUTHOR`
* `USER`

---

## 🔐 Security Model

### Authentication

* **JWT-based authentication**
* Managed by Spring Security
* BCrypt password hashing

### Authorization

* Role-Based Access Control (RBAC)
* Ownership validation enforced in services
* Method-level security

---

## 🔒 Authorization Rules

| Capability       | Role Requirement |
| ---------------- | ---------------- |
| Register / Login | Public           |
| Create Post      | AUTHOR           |
| Edit Own Post    | AUTHOR           |
| Edit Any Post    | ADMIN            |
| Publish Post     | AUTHOR           |
| View Posts       | Public           |

---

## 🧩 Application Phases

---

### 🟢 Phase 1 — Authentication (Auth Module)

#### Auth Domain Model

* Email (unique)
* BCrypt-hashed password
* Role

**Responsibilities**

* User registration and login.
* Manages authentication and sessions.
* Defines user roles (`ADMIN`, `AUTHOR`, `USER`).

---

### 🟢 Phase 2 — User Profiles (Profile Module)

#### Profile Entity

* Reference to user account via ID
* Display name
* Bio
* Avatar URL

**Separation**

* Manages user profiles.
* Allows users to view and update their profiles.

---

### 🟢 Phase 3 — Posts & Publishing (Posts Module)

#### Post Entity

* Author reference via user ID
* Title
* Content
* Status (`DRAFT`, `PUBLISHED`, `ARCHIVED`)
* Creation & update timestamps

#### Business Rules

* Handles the creation, updating, deletion, and retrieval of posts.
* Includes functionality for image uploads with posts.

---

### 🟢 Phase 4 — File Storage (Storage Module)

#### Storage Responsibilities

* Upload files to **Google Cloud Storage**
* Generate public or signed URLs
* Store file metadata

**Abstraction**

* Handles file storage, specifically for post images.
* Interacts with Google Cloud Storage.

---

## 📡 REST API Endpoints (All Modules)

---

### 🔐 Auth Module

| Method | Endpoint         | Description           | Access |
| ------ | ---------------- | --------------------- | ------ |
| POST   | `/auth/register` | Register new user     | Public |
| POST   | `/auth/login`    | Login (session-based) | Public |

---

### 🧾 Profile Module

| Method | Endpoint                     | Description        | Access |
| ------ | ---------------------------- | ------------------ | ------ |
| GET    | `/profile/{username}`        | Get public profile | Public |
| PUT    | `/profile/update/{username}` | Update own profile | Auth   |

---

### 📝 Posts Module

| Method | Endpoint                 | Description       | Access         |
| ------ | ------------------------ | ----------------- | -------------- |
| POST   | `/posts`                 | Create post       | AUTHOR         |
| PUT    | `/posts/update/{postId}` | Edit post         | AUTHOR / ADMIN |
| DELETE | `/posts/delete/{postId}` | Delete post       | AUTHOR / ADMIN |
| GET    | `/posts/user/{username}` | List user's posts | Public         |

---

## 🧪 Testing Strategy

| Level       | Scope            | Tools          |
| ----------- | ---------------- | -------------- |
| Unit        | Service logic    | JUnit, Mockito |
| Module      | Isolation tests  | Testcontainers |
| Integration | End-to-end flows | SpringBootTest |
| Security    | Auth & RBAC      | Manual / OWASP |

---

## 🐳 Deployment

* Dockerized Spring Boot application
* Deployed to **Google Cloud Platform**
* **JWT-based authentication** is used.
* **Cloud SQL** for MySQL
* **Google Cloud Storage** for files

---
