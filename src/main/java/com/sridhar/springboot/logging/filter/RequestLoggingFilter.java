package com.sridhar.springboot.logging.filter;

import com.sridhar.springboot.logging.constants.LoggingConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        String requestId = UUID.randomUUID().toString();
        try {
            MDC.put(LoggingConstants.REQUEST_ID, requestId);
            MDC.put(LoggingConstants.CLIENT_IP, getClientIp(request));
            MDC.put(LoggingConstants.USER_AGENT, request.getHeader(LoggingConstants.USER_AGENT));

            log.info(
                    "Incoming Request : {} {}",
                    request.getMethod(),
                    request.getRequestURI()
            );
            filterChain.doFilter(request, response);
        } finally {
            long executionTime =
                    System.currentTimeMillis() - startTime;
            log.info(
                    "REQUEST COMPLETED : {} {} Status={} Time={}ms",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    executionTime
            );
            MDC.remove(LoggingConstants.REQUEST_ID);
        }
    }

    private String getClientIp(HttpServletRequest request){
        String forwarded = request.getHeader(LoggingConstants.HEADER_FORWARDED_FOR);
        String clientIp;
        if (forwarded != null && !forwarded.isBlank()) {
            clientIp = forwarded.split(",")[0].trim();
        } else {
            clientIp = request.getRemoteAddr();
        }
        MDC.put(LoggingConstants.CLIENT_IP, clientIp);
        return request.getRemoteAddr();
    }
}



//
//
//Excellent observation. You're thinking like a software engineer now.
//
//        **The answer is: Yes, we have only used `RequestLoggingFilter` so far.** The other classes are part of a **production logging architecture**. We created the package structure first, and now we'll implement each component one by one.
//
//Let's go through every file.
//
//        ---
//
//        # Current Progress
//
//```
//logging
//│
//        ├── aspect
//│     LoggingAspect          ❌ Not Used Yet
//│
//        ├── audit
//│     AuditLogger            ❌ Not Used Yet
//│
//        ├── config
//│     LoggingConfig          ❌ Not Used Yet
//│
//        ├── constants
//│     LoggingConstants       ❌ Not Used Yet
//│
//        ├── filter
//│     RequestLoggingFilter   ✅ Used
//│
//        ├── interceptor
//│     LoggingInterceptor     ❌ Not Used Yet
//│
//        ├── mdc
//│     TraceIdManager         ❌ Not Used Yet
//│
//        └── util
//LogMaskingUtil         ❌ Not Used Yet
//```
//
//Only one file is active because we have only completed **Request Logging**.
//
//        ---
//
//        # 1. RequestLoggingFilter ✅
//
//Already implemented.
//
//Purpose:
//
//        ```
//Incoming Request
//
//↓
//
//Generate Request ID
//
//↓
//
//Store in MDC
//
//↓
//
//Log Request
//
//↓
//
//Continue Request
//
//↓
//
//Log Response Time
//
//↓
//
//Clear MDC
//```
//
//Example log
//
//```
//Incoming Request : POST /student/add
//
//        RequestId = 1234
//
//Client IP = xxx
//
//Response Time = 52 ms
//```
//
//This is the first building block.
//
//        ---
//
//        # 2. LoggingAspect
//
//        Purpose
//
//Automatically log every Controller and Service method.
//
//        Without Aspect
//
//```java
//log.info("Entering addStudent");
//
//// business
//
//log.info("Completed addStudent");
//```
//
//Every method needs logging.
//
//        100 methods?
//
//        100 log statements.
//
//Very repetitive.
//
//        ---
//
//With Aspect
//
//```
//Controller
//
//↓
//
//Aspect logs Entry
//
//↓
//
//Method executes
//
//↓
//
//Aspect logs Exit
//
//↓
//
//Controller
//```
//
//Example
//
//Instead of
//
//```java
//public Student save(...)
//{
//    log.info("Enter");
//
//    ...
//
//    log.info("Exit");
//}
//```
//
//Just
//
//```java
//public Student save(...)
//{
//
//}
//```
//
//Aspect prints automatically
//
//```
//Entering StudentService.addStudent()
//
//Leaving StudentService.addStudent()
//
//Execution Time = 18ms
//```
//
//        ---
//
//        # 3. AuditLogger
//
//This is NOT normal logging.
//
//Audit logging means
//
//        WHO
//
//did WHAT
//
//WHEN
//
//FROM WHERE
//
//Example
//
//```
//User admin
//
//Deleted Student
//
//ID = 123
//
//Time
//
//        IP
//```
//
//Audit Log
//
//```
//        2026-07-13
//
//ADMIN
//
//Deleted Student
//
//123
//
//        192.168.1.20
//        ```
//
//Stored separately.
//
//Usually
//
//```
//audit.log
//```
//
//Never mixed with application logs.
//
//Banks use this.
//
//Insurance companies use this.
//
//Healthcare uses this.
//
//        ---
//
//        # 4. LoggingConfig
//
//Right now
//
//Every class
//
//```
//        MDC.put()
//
//MDC.remove()
//
//Patterns
//
//        etc
//```
//
//If configuration grows
//
//it becomes messy.
//
//        LoggingConfig centralizes
//
//```
//Logging Bean
//
//MDC configuration
//
//Filter registration
//
//Aspect configuration
//
//Converters
//```
//
//Basically
//
//Application-wide logging configuration.
//
//---
//
//        # 5. LoggingConstants
//
//Instead of
//
//```java
//MDC.put("requestId",value);
//
//MDC.put("clientIp",value);
//
//MDC.put("userAgent",value);
//```
//
//Use
//
//```java
//LoggingConstants.REQUEST_ID
//
//LoggingConstants.CLIENT_IP
//
//LoggingConstants.USER_AGENT
//```
//
//Advantages
//
//No spelling mistakes.
//
//Easy refactoring.
//
//One place to modify.
//
//        ---
//
//        # 6. LoggingInterceptor
//
//        Difference
//
//Filter
//
//↓
//
//Runs before Spring MVC
//
//Interceptor
//
//↓
//
//Runs after DispatcherServlet
//
//```
//Filter
//
//↓
//
//DispatcherServlet
//
//↓
//
//Interceptor
//
//↓
//
//Controller
//```
//
//Interceptor can know
//
//```
//Handler Method
//
//Controller Name
//
//Annotations
//
//Authenticated User
//
//Roles
//```
//
//Filter cannot.
//
//Example
//
//```
//Controller
//
//↓
//
//StudentController
//
//↓
//
//Method
//
//addStudent()
//```
//
//Interceptor knows this.
//
//Useful for
//
//        ```
//Controller Performance
//
//Authorization Logging
//
//User Activity
//```
//
//        ---
//
//        # 7. TraceIdManager
//
//        Currently
//
//RequestLoggingFilter generates UUID.
//
//        Later
//
//        Microservices
//
//```
//Gateway
//
//↓
//
//Order Service
//
//↓
//
//Payment Service
//
//↓
//
//Inventory Service
//```
//
//Need same Trace ID.
//
//TraceIdManager handles
//
//```
//Generate
//
//        Reuse
//
//Read Header
//
//Store
//
//        Remove
//```
//
//Instead of writing
//
//```
//        UUID.randomUUID()
//```
//
//everywhere.
//
//---
//
//        # 8. LogMaskingUtil
//
//One of the most important.
//
//Suppose someone logs
//
//```java
//log.info(request.toString());
//        ```
//
//Output
//
//```
//Password=password123
//
//        Card=1234123412341234
//
//JWT=eyJhb...
//        ```
//
//Very dangerous.
//
//Masking Utility changes
//
//```
//Password=******
//
//Card=********1234
//
//JWT=********
//        ```
//
//Automatically.
//
//Banks always do this.
//
//        ---
//
//        # Final Architecture
//
//```
//HTTP Request
//                        │
//                                ▼
//RequestLoggingFilter
//                        │
//                                ▼
//TraceIdManager
//                        │
//                                ▼
//LoggingInterceptor
//                        │
//                                ▼
//Controller
//                        │
//                                ▼
//LoggingAspect
//                        │
//                                ▼
//Service Layer
//                        │
//                                ▼
//AuditLogger (Business Events)
//                        │
//                                ▼
//LogMaskingUtil (Hide Secrets)
//                        │
//                                ▼
//JSON Log / Async Log / Logback
//                        │
//                                ▼
//ELK / Splunk / Grafana
//```
//
//        ## Why I asked you to create the folders first
//
//The goal wasn't to use every class immediately. It was to establish a **clean, scalable package structure**. In enterprise projects, it's common to create the architecture up front and implement each component as the application evolves.
//
//We're following the same progression.
//
//        ### Our learning roadmap
//
//* ✅ Request Logging (`RequestLoggingFilter`)
//* ⏳ Logging Constants
//* ⏳ Trace ID Manager
//* ⏳ Logging Aspect (AOP)
//* ⏳ Logging Interceptor
//* ⏳ Audit Logger
//* ⏳ Log Masking Utility
//* ⏳ Structured JSON Logging
//* ⏳ Async Logging
//* ⏳ ELK Stack Integration
//
//By the end, you'll have a logging module that closely resembles what you'd find in a production Spring Boot microservices application.
