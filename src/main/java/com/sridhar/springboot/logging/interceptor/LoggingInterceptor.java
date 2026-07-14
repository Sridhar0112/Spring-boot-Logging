package com.sridhar.springboot.logging.interceptor;

import com.sridhar.springboot.logging.constants.LoggingConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        request.setAttribute(LoggingConstants.START_TIME, System.currentTimeMillis());

        if (handler instanceof HandlerMethod handlerMethod) {

            log.info(
                    "Controller={} Method={} started",
                    handlerMethod.getBeanType().getSimpleName(),
                    handlerMethod.getMethod().getName()
            );
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        Long startTime = (Long) request.getAttribute(LoggingConstants.START_TIME);

        long executionTime = System.currentTimeMillis() - startTime;

        if (handler instanceof HandlerMethod handlerMethod) {

            log.info(
                    "Controller={} Method={} completed Status={} Time={} ms",
                    handlerMethod.getBeanType().getSimpleName(),
                    handlerMethod.getMethod().getName(),
                    response.getStatus(),
                    executionTime
            );
        }
    }
}