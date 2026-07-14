package com.sridhar.springboot.logging.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.sridhar.springboot.Service.*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        String className =
                joinPoint.getTarget()
                        .getClass()
                        .getSimpleName();

        String methodName =
                joinPoint.getSignature()
                        .getName();

        log.info(
                "Entering {}.{}()",
                className,
                methodName
        );

        try {

            Object result = joinPoint.proceed();
            long executionTime =
                    System.currentTimeMillis()
                            - startTime;

            log.info(
                    "Completed {}.{}() in {} ms",
                    className,
                    methodName,
                    executionTime
            );

            return result;

        } catch (Exception ex) {

            long executionTime =
                    System.currentTimeMillis()
                            - startTime;

            log.error(
                    "Exception in {}.{}() after {} ms : {}",
                    className,
                    methodName,
                    executionTime,
                    ex.getMessage()
            );

            throw ex;
        }
    }
}