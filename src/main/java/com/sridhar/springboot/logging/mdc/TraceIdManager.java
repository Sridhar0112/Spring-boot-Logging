package com.sridhar.springboot.logging.mdc;

import com.sridhar.springboot.logging.constants.LoggingConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class TraceIdManager {

    public String startTrace(HttpServletRequest request) {
        String requestId =
                request.getHeader(LoggingConstants.HEADER_REQUEST_ID);
        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }
        MDC.put(LoggingConstants.REQUEST_ID, requestId);
        return requestId;
    }

    public void clear() {
        MDC.remove(LoggingConstants.REQUEST_ID);
        MDC.remove(LoggingConstants.CLIENT_IP);
        MDC.remove(LoggingConstants.USER_AGENT);
    }

}