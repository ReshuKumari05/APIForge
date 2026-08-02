<div align="center">

# 🚀 APIForge

### Enterprise-Ready REST API Management Platform

Secure, scalable, and production-ready backend platform for building, managing, documenting, and testing REST APIs with JWT Authentication, Role-Based Authorization, Docker, and Swagger.

<p>

<img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge"/>
<img src="https://img.shields.io/badge/Spring_Boot-3.5.4-6DB33F?style=for-the-badge"/>
<img src="https://img.shields.io/badge/Spring_Security-6-6DB33F?style=for-the-badge"/>
<img src="https://img.shields.io/badge/MySQL-8-4479A1?style=for-the-badge"/>
<img src="https://img.shields.io/badge/JWT-Authentication-red?style=for-the-badge"/>
<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge"/>

</p>

*A production-ready backend platform built using Spring Boot following modern software engineering principles.*

</div>

---

# 📌 Overview

APIForge is a full-stack backend platform designed for building, securing, documenting, and managing RESTful APIs. The application provides secure authentication using JWT, role-based authorization, project management, endpoint management, execution tracking, analytics, and comprehensive API documentation through Swagger/OpenAPI.

The project follows a clean layered architecture with Controller, Service, Repository, DTO, and Entity layers, ensuring maintainability, scalability, and separation of concerns. Docker support, environment-based configuration, and automated API documentation make the platform deployment-ready for modern backend environments.

---

## 📑 Table of Contents

- 📸 Application Preview
- ✨ Features
- 🔐 Authentication & Authorization
- 📡 API Modules
- 📊 Analytics Dashboard
- 🏗 System Architecture
- 📂 Project Structure
- ⚙ Technology Stack
- 🐳 Docker Support
- 📖 Swagger Documentation
- 🚀 Getting Started
- 🔧 Environment Configuration
- 🧪 Testing
- 📈 Future Improvements
- 👨‍💻 Author

---
# ✨ Features

### 🔐 Authentication & Security

- JWT-based Authentication
- Role-Based Authorization (ADMIN / USER)
- Spring Security Integration
- BCrypt Password Encryption
- Secure API Access Control
- Stateless Authentication

---

### 📡 API Management

- Project Management APIs
- API Endpoint Management
- API Execution Engine
- Execution History Tracking
- API Analytics Dashboard
- Administrative APIs

---

### ⚙️ Backend Engineering

- RESTful API Design
- Layered Architecture
- DTO Pattern
- Repository Pattern
- Global Exception Handling
- Request Validation
- Pagination Support
- Standard HTTP Status Codes
- Environment-based Configuration

---

### 📦 Production Features

- Swagger / OpenAPI Documentation
- Docker Support
- Docker Compose Configuration
- MySQL Database
- Maven Build System
- Unit Testing
- Containerized using Docker

  # 🏗️ System Architecture

APIForge follows a production-oriented layered architecture that promotes separation of concerns, maintainability, scalability, and secure API development.

```text
                         Client Applications
                    (React / Postman / Swagger)
                               │
                               ▼
                    Spring Security + JWT Filter
                               │
                               ▼
                      REST Controllers Layer
                               │
                               ▼
                      Business Service Layer
                               │
                               ▼
                       Repository Layer (JPA)
                               │
                               ▼
                           MySQL Database
```

### Architectural Highlights

- Layered Architecture
- DTO-based Request & Response Models
- Repository Pattern
- Service-Oriented Business Logic
- JWT Authentication Pipeline
- Role-Based Authorization
- Global Exception Handling
- Environment-based Configuration

  # 📂 Project Structure

```text
APIForge
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.reshu.apiforge
│   │   │       ├── config
│   │   │       ├── controller
│   │   │       ├── dto
│   │   │       ├── entity
│   │   │       ├── exception
│   │   │       ├── repository
│   │   │       ├── security
│   │   │       ├── service
│   │   │       └── ApiforgeApplication
│   │   │
│   │   └── resources
│   │       ├── static
│   │       ├── templates
│   │       └── application.properties
│   │
│   └── test
│       └── java
│
├── Dockerfile
├── compose.yaml
├── .env.example
├── pom.xml
└── README.md
```
# 📡 API Modules

APIForge provides multiple REST API modules for secure application management and API lifecycle management.

| Module | Description |
|---------|-------------|
| 🔐 Authentication | User Registration, Login & JWT Authentication |
| 👤 User Management | User profile and account management |
| 📁 Project Management | Create, update, delete and manage projects |
| 🌐 API Endpoint Management | Manage REST API endpoints under projects |
| ▶ API Execution | Execute stored API endpoints |
| 📜 Execution History | Track previous API executions |
| 📊 Analytics Dashboard | Repository statistics and usage insights |
| 👑 Admin Dashboard | Administrative operations and monitoring |

---
# 🔐 Authentication & Authorization

APIForge secures every protected endpoint using JWT Authentication and Spring Security.

### Security Workflow

```text
User Login
     │
     ▼
Authenticate Credentials
     │
Generate JWT Token
     │
Client stores JWT
     │
Bearer Token
     │
JWT Authentication Filter
     │
Spring Security
     │
Role-Based Authorization
     │
Protected REST APIs
```

### Security Features

- JWT Authentication
- Role-Based Authorization
- Spring Security
- BCrypt Password Encryption
- Stateless Authentication
- Protected REST Endpoints

---
# 🐳 Docker Support

APIForge is fully containerized for simplified deployment.

Included Docker resources:

- Dockerfile
- Docker Compose
- Environment Configuration
- Production-ready Build

Run using Docker:

```bash
docker compose up --build
```

---
# ⚙️ Technology Stack

## Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT
- Maven

## Database

- MySQL

## Documentation

- Swagger / OpenAPI

## DevOps

- Docker
- Docker Compose

## Testing

- JUnit
- Spring Boot Test

---
# 🚀 Getting Started

## Clone Repository

```bash
git clone https://github.com/ReshuKumari05/APIForge.git
```

## Configure Environment

```properties
DB_URL=
DB_USERNAME=
DB_PASSWORD=
JWT_SECRET=
```

or simply copy

```text
.env.example
```

to

```text
.env
```

and update the values.

---

## Build Project

```bash
mvn clean install
```

---

## Run Application

```bash
mvn spring-boot:run
```

---

## Swagger Documentation

After starting the application:

```text
http://localhost:8080/swagger-ui/index.html
```

---
# 🧪 Testing

APIForge includes testing support for validating API functionality and backend components.

### Testing Stack

- Spring Boot Test
- JUnit
- HTTP Request Collection (`test.http`)
- Swagger UI for Interactive API Testing

API endpoints can be tested through:

- Swagger UI
- Postman
- IntelliJ HTTP Client

  # 📈 Future Improvements

- Refresh Token Authentication
- Email Verification
- Password Reset Workflow
- API Rate Limiting
- Redis Caching
- CI/CD Pipeline
- Kubernetes Deployment
- Monitoring & Logging
- Multi-Tenant API Management
- API 

# 👨‍💻 Author

**Reshu Kumari**

Backend Developer specializing in Java, Spring Boot, REST APIs, and secure backend systems.

- GitHub: https://github.com/ReshuKumari05
- LinkedIn: https://www.linkedin.com/in/reshu-kumari-7b72b0292/

---

⭐ If you found this project useful, consider giving it a star.
