package com.sridhar.springboot.logging.constants;

public final class LoggingConstants {

    private LoggingConstants() {
    }
    // MDC Keys
    public static final String REQUEST_ID = "requestId";
    public static final String TRACE_ID = "traceId";
    public static final String CLIENT_IP = "clientIp";
    public static final String USER_AGENT = "userAgent";
    // HTTP Headers
    public static final String HEADER_REQUEST_ID = "X-Request-ID";
    public static final String HEADER_TRACE_ID = "X-Trace-ID";
    public static final String HEADER_FORWARDED_FOR = "X-Forwarded-For";
    // Log Messages
    public static final String REQUEST_START = "Incoming Request";
    public static final String REQUEST_END = "Request Completed";
    public static final String START_TIME = "startTime";
    public static final String SERVICE_NAME = "student-service";
}
