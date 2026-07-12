# Spring Boot Exception Handling

A Spring Boot project demonstrating **enterprise-level exception handling** using industry best practices.

This project shows how to build clean, maintainable REST APIs by separating business logic from exception handling and returning consistent error responses.

---

## 🚀 Features

- Custom Exceptions
- Global Exception Handling using `@RestControllerAdvice`
- `@ExceptionHandler` implementation
- Custom Error Response DTO
- Clean Controller Layer
- Service Layer Exception Propagation
- Proper HTTP Status Codes
- Constructor-Based Dependency Injection
- REST API Best Practices

---

## 📂 Project Structure

```
Controller
     │
     ▼
Service
     │
     ▼
Repository
     │
     ▼
Database

        ▲
        │
Global Exception Handler
        │
        ▼
Custom Error Response
```

---

## 🛠 Technologies Used

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Maven

---

## 📌 Exception Handling Flow

```
Client Request
      │
      ▼
Controller
      │
      ▼
Service
      │
      ▼
Repository

Exception Occurs
      │
      ▼
GlobalExceptionHandler
      │
      ▼
Custom Error Response
      │
      ▼
HTTP Response
```

---

## 📖 Concepts Covered

- Custom Exception Creation
- Global Exception Handling
- Exception Propagation
- DTO Pattern
- ResponseEntity
- REST API Error Handling
- Clean Code Principles
- Enterprise Spring Boot Architecture

---

## 📚 Learning Outcome

This project demonstrates how enterprise Spring Boot applications handle exceptions centrally instead of using repetitive `try-catch` blocks inside controllers. It follows clean architecture principles by keeping controllers lightweight and delegating error handling to a global exception handler.

---

## 👨‍💻 Author

Sridhar
