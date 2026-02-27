# 🏦 Secure Digital Banking Backend

A secure and scalable Digital Banking Backend System built using Spring Boot.

## 🚀 Tech Stack

- Java 21
- Spring Boot 4
- Spring Security
- JWT Authentication
- MySQL
- Hibernate / JPA
- Maven
- Swagger (OpenAPI)
- SLF4J Logging

---

## 🔐 Features

- User Registration & Login
- JWT-based Authentication
- BCrypt Password Encryption
- Account Creation (One user → Multiple accounts)
- Deposit & Withdraw APIs
- Fund Transfer with Transaction Rollback
- Transaction History
- Account Ownership Validation
- Global Exception Handling
- Structured Logging

---

## 🏗 Architecture

Controller → Service → Repository  
DTO Layer  
JWT Security Filter  
MySQL Database  

---

## 🧪 API Documentation

Swagger UI available at:
http://localhost:8082/swagger-ui/index.html
---

## ⚙️ How To Run Locally

1. Clone repository
2. Configure MySQL in `application.properties`
3. Run:
mvn clean install
mvn spring-boot:run
---

## 📌 Future Improvements

- Role-based Authorization (Admin/User)
- Docker Deployment
- Cloud Deployment (Render/AWS)
- Monitoring & Logging Integration

---

## 👩‍💻 Author

Muskan Neema  
Java Backend Developer