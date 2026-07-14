package com.sridhar.springboot.logging.filter;

import com.sridhar.springboot.logging.constants.LoggingConstants;
import com.sridhar.springboot.logging.mdc.TraceIdManager;
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
    private final TraceIdManager traceIdManager;
    public RequestLoggingFilter(
            TraceIdManager traceIdManager
    ){
        this.traceIdManager = traceIdManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        String requestId = traceIdManager.startTrace(request);
        try {
            log.info("Incoming Request : {} {}", request.getMethod(), request.getRequestURI());
            MDC.put("service", "student-service");
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
            traceIdManager.clear();
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
