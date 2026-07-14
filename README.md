# Spring Boot Enterprise Logging

A production-ready Spring Boot application demonstrating **enterprise-grade logging best practices**.

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen)
![Logback](https://img.shields.io/badge/Logback-Logging-blue)

---

## **Project Overview**

This project is a **Student Management REST API** built with Spring Boot, showcasing a complete **Enterprise Logging Implementation**.

It combines clean CRUD functionality with comprehensive observability, traceability, and production-ready logging practices.

---

## **Key Features**

### **Core Functionality**
- Student CRUD Operations (Create, Read)
- Spring Data JPA with H2 Database
- Jakarta Bean Validation with Custom Validator
- Global Exception Handling

### **Enterprise Logging Highlights**
- **Multi-layer Logging**: Filter + Interceptor + AOP Aspect
- **Correlation ID** using MDC for full request tracing
- **Client Context**: IP Address, User-Agent logging
- **Custom Logback Configuration** with rolling policies
- **Separate Log Files**: `application.log`, `error.log`, `debug.log`
- **Request & Response Logging**
- **Service Layer AOP Logging**
- **Sensitive Data Masking Utility**
- **Structured Logging** ready for ELK / centralized systems

---

## **Logging Architecture**

### **Layers & Components**
- **RequestLoggingFilter** (`OncePerRequestFilter`) — Entry/Exit logging + Correlation ID
- **LoggingInterceptor** — Controller level tracking
- **LoggingAspect** — Service layer method execution tracking
- **GlobalExceptionHandler** — Centralized error logging
- **TraceIdManager** — MDC management
- **LoggingMaskUtil** — Data sanitization

### **Log Levels & Files**
- `application.log` — General application logs
- `error.log` — WARN + ERROR logs
- `debug.log` — Detailed debug information
- Console output with rich context

---

## **Tech Stack**

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Web, Data JPA, Validation, AOP**
- **Logback + Logstash Encoder**
- **Lombok**
- **H2 Database** (In-memory)
- **Maven**

---

## **Project Structure**

```
src/main/java/com/sridhar/springboot/
├── logging/                  # Core logging module
│   ├── filter/
│   ├── interceptor/
│   ├── aspect/
│   ├── mdc/
│   ├── config/
│   ├── util/
│   └── constants/
├── Controller/
├── Service/
├── Repositary/
├── Exception/
├── Validation/
├── Dto/
├── models/
└── SpringbootApplication.java
```

---

## **How to Run**

1. Clone the repository:
   ```bash
   git clone https://github.com/Sridhar0112/Spring-boot-Logging.git
   ```

2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Test the APIs:
   - `POST /student/add`
   - `GET /student/getstudent`
   - `GET /student/getstudent/{id}`

Logs will be generated in the `/logs` directory.

---

## **Logging Demonstration**

Every request is fully traceable using `requestId`. Check the generated log files to see:
- Incoming request details
- Execution timing per layer
- Client information
- Structured context
- Error scenarios with full stack traces

---

## **Enterprise Best Practices Demonstrated**

- Separation of concerns (Business logic vs Logging)
- Full request lifecycle observability
- Correlation ID for distributed tracing readiness
- Configurable rolling file appenders
- Data masking for security
- Multi-environment logging support

---


**Author**: Sridhar  
**Purpose**: Enterprise Spring Boot Logging Reference Implementation
