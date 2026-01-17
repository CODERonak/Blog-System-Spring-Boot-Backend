
---

# 📝 Spring Boot Blog Platform Backend API

A **Blog / Content Platform backend** built with **Spring Boot**, designed as a **Modular Monolith** with **strict domain isolation**, **SOLID principles**, **Clean Architecture**.

This project emphasizes **long-term maintainability**, **scalability**, and **correctness**, while remaining deployable as a **single application** to **Google Cloud Platform (GCP)**, utilizing **Cloud Storage** for media and **Cloud SQL** for the database.


---

## 🎯 Project Goals

* Build a **production-ready backend** using Spring Boot
* Enforce **strong modular boundaries** inside a monolith
* Apply **SOLID principles** at module and service levels
* Ensure **correctness under concurrent access**
* Optimize read-heavy workloads with **Redis caching**
* Design the system to be **microservice-extractable later**

---

## 🧱 Architectural Philosophy — Modular Monolith

### What Modular Monolith Means Here

* Single deployable application (**one JAR**)
* Strong internal module boundaries
* Each module owns **one business domain**
* **No shared database tables** across modules
* Inter-module communication **only via interfaces**
* No direct entity or repository sharing

> This is **not** a “large package with folders”.  
> This is a **well-structured system** that can be safely split later.

---

## 🚫 Hard Architectural Rules (Non-Negotiable)

* A module **cannot** access another module’s repository
* A module **cannot** access another module’s entities
* Cross-module interaction happens **only via public service interfaces**
* Controllers talk **only** to their own module
* DTOs never leak entities
* Infrastructure code never leaks into domain logic

Violating these rules breaks modularity.

---

## 🧩 Key Engineering Principles

* **Single Responsibility Principle (SRP)** — one business capability per module
* **Open/Closed Principle (OCP)** — extend behavior via new services
* **Liskov Substitution Principle (LSP)** — interchangeable service implementations
* **Interface Segregation Principle (ISP)** — small, module-level interfaces
* **Dependency Inversion Principle (DIP)** — controllers depend on interfaces
* DTOs at system boundaries
* Service-layer business logic
* Repository abstraction with JPA
* Centralized exception handling

---

## 🛠 Tech Stack

* **Java 21+**
* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **Spring Security**
* **MySQL**
* **Redis**
* **BCrypt Password Hashing**
* **Spring Validation**
* **Spring Actuator**
* **Docker / Docker Compose**

---

## 🗂️ High-Level Module Map

```

com.example.blog
│
├── common        (shared kernel, very small)
├── auth          (authentication & identity)
├── users         (user profiles)
├── posts         (posts & publishing)
├── comments      (commenting system)
├── media         (file metadata & storage abstraction)
├── cache         (Redis abstractions)
└── BlogApplication.java

```

Only `common` is shared.  
All other modules are **fully isolated**.

---

## 🧱 Internal Module Structure

Each module follows the **same internal architecture**:

```

module-name/
├── api           (DTOs + public interfaces)
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

This consistency is intentional and team-scalable.

---

## 🔐 Security Model

### Phase 1 — Session-Based Authentication

* Stateful session-based authentication
* Spring Security–managed sessions
* BCrypt password hashing
* Role-Based Access Control (RBAC)
* CSRF enabled

### Phase 2 — JWT + Redis (Planned)

* Stateless authentication
* JWT access tokens
* Redis-backed token blacklist
* Rate limiting counters

---

## 👥 User Roles

* `ADMIN`
* `AUTHOR`
* `USER`

---

## 🔒 Authorization Rules (High-Level)

| Capability                | Role Requirement            |
|--------------------------|-----------------------------|
| Register / Login         | Public                      |
| Create Post              | AUTHOR                      |
| Edit Own Post            | AUTHOR                      |
| Edit Any Post            | ADMIN                       |
| Publish Post             | AUTHOR                      |
| Comment                  | USER, AUTHOR, ADMIN         |
| Moderate Content         | ADMIN                       |

---

## 🔑 Authentication Endpoints

| Method | Endpoint         | Description           | Access |
|------|------------------|----------------------|--------|
| POST | `/auth/register` | Register new user     | Public |
| POST | `/auth/login`    | Login (session-based)| Public |

---

## 🧩 Application Phases

---

### 🟢 Phase 0 – System Initialization

* Spring Boot project setup
* Database & Redis configuration
* Modular package structure
* Health check verification

**Status**
* ✅ Application starts correctly
* ✅ Database connectivity verified
* ✅ Redis connectivity verified

---

### 🟢 Phase 1 – Authentication & Identity (Auth Module)

#### Auth Domain Model

* Email (unique)
* BCrypt-hashed password
* Role-based access
* Account enabled flag
* Creation timestamp

**Key Concepts**
* Authentication vs Authorization
* Session lifecycle
* Thread-local `SecurityContext`

---

### 🟢 Phase 2 – User Profiles (Users Module)

Auth and user profile are **separate concerns**.

#### User Profile Entity

* Reference to Auth user via ID
* Display name
* Bio
* Creation & update timestamps

**Rule**
* Users module stores `authUserId`, not Auth entities
* Auth is accessed only via `AuthService`

---

### 🟢 Phase 3 – Posts & Publishing (Core Domain)

#### Post Entity

* Author reference via Auth user ID
* Title & content
* Status lifecycle (`DRAFT → PUBLISHED → ARCHIVED`)
* Timestamps

#### Key Features

* Role-based post creation
* Ownership validation
* Pagination mandatory
* Indexed queries
* Status-based filtering

**CS Fundamentals**
* Pagination vs offset cost
* Index usage
* Transaction boundaries

---

### 🟢 Phase 4 – Commenting System

#### Comment Model

* Self-referencing (parent-child)
* Max depth = 3
* Soft delete only
* No cascade deletes

**Key Concepts**
* Tree structures
* Recursive reads
* N+1 query prevention

---

### 🟢 Phase 5 – Caching Layer (Redis)

Cache is a **dedicated module**, not scattered annotations.

#### Cache Use Cases

| Data         | Redis Key        | TTL     |
|--------------|------------------|---------|
| Post pages   | `posts:page:{n}` | 5 min   |
| Single post  | `post:{id}`      | 10 min  |
| User profile | `user:{id}`      | 15 min  |

**Pattern**
* Cache-aside
* Explicit invalidation
* TTL-based eviction

---

### 🟢 Phase 6 – Media Management

#### Media Entity

* Owner reference
* File path
* Media type (IMAGE / VIDEO)
* Creation timestamp

#### Storage Abstraction

* Local filesystem (Phase 1)
* Cloud storage (S3 / GCS) later
* No API changes required

---

### 🟢 Phase 7 – Security Upgrade (Planned)

* Stateless JWT authentication
* Redis-backed token blacklist
* Rate limiting
* Replay attack mitigation

---

## ⚙️ System Design Fundamentals

### Data Relationships

* Auth → Users (1:1 via ID)
* Users → Posts (1:N)
* Posts → Comments (1:N)
* Users → Media (1:N)

### Transaction Strategy

* Service layer = transaction boundary
* Writes are transactional
* Reads optimized and cached

### Scalability Model

* Stateless application
* Horizontal scaling
* Shared Redis cache
* DB read replicas later

---

## 🧪 Testing Strategy

| Level       | Scope             | Tools               |
|------------|-------------------|---------------------|
| Unit       | Service logic     | JUnit, Mockito      |
| Module     | Module isolation  | Testcontainers      |
| Integration| Cross-module      | SpringBootTest      |
| Load       | Read-heavy paths  | k6                  |
| Security   | Auth flows        | Manual + OWASP      |

---

## 📊 Actuator Endpoints

| Endpoint            | Description               |
|---------------------|---------------------------|
| `/actuator/health`  | Application health        |
| `/actuator/metrics` | JVM & application metrics |

---

## 🐳 Deployment

* Deployed to Google Cloud Platform
* Google Cloud Storage for media files
* Google Cloud SQL for database


---

## ✅ Final Summary

This Blog Platform backend is:

* ✅ Strictly modular
* ✅ SOLID-compliant
* ✅ Redis-enabled
* ✅ Security-aware
* ✅ System-design driven
* ✅ Ready to scale or split into microservices

**This is not a demo project.  
This is a foundation for real systems.**

---
