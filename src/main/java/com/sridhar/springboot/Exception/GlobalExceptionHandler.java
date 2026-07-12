package com.sridhar.springboot.Exception;

import com.sridhar.springboot.Dto.StudentDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<?> handleStudentException(
            StudentException ex,HttpServletRequest request){
        log.warn(
                "Student Exception occurred URI={} Message={}",
                request.getRequestURI(),
                ex.getMessage()
        );
        StudentDto.ApiErrorResponse error =
                new StudentDto.ApiErrorResponse(
                        LocalDateTime.now(),
                        400,
                        ex.getMessage(),
                        request.getRequestURI(),
                        null
                );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request
    ){

        Map<String, List<String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        error -> error.getField(),
                        Collectors.mapping(
                                error -> error.getDefaultMessage(),
                                Collectors.toList()
                        )
                ));
        StudentDto.ApiErrorResponse error= new StudentDto.ApiErrorResponse(
                LocalDateTime.now(), 400,"Validation Failed - Invalid Request",request.getRequestURI(),errors
        );
        log.error(
                "Validation Failed URI={} Errors={}",
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception ex,HttpServletRequest req) {
        log.error(
                "Unexpected Error URI={}",
                req.getRequestURI(),
                ex
        );
        StudentDto.ApiErrorResponse error =
                new StudentDto.ApiErrorResponse(
                        LocalDateTime.now(),
                        500,
                        "Internal Server Error",
                        req.getRequestURI(),
                        null
                );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> handleMethodValidationException(HandlerMethodValidationException ex, HttpServletRequest request) {
        List<ParameterValidationResult>parameterValidationResults= ex.getParameterValidationResults();

        Map<String, List<String>> errors =
                new LinkedHashMap<>();

        for(ParameterValidationResult result:parameterValidationResults){
            String fieldName=result.
                    getMethodParameter().getParameterName();
            List<String>errorMessage=result
                    .getResolvableErrors()
                    .stream()
                    .map(a->a.getDefaultMessage())
                    .toList();
            errors.put(fieldName,errorMessage);
        }

        StudentDto.ApiErrorResponse error= new StudentDto.ApiErrorResponse(
                LocalDateTime.now(), 400,"Validation Failed - Invalid Request",request.getRequestURI(),errors
        );
        log.error(
                "Method Validation Failed URI={} Errors={}",
                request.getRequestURI(),
                errors
        );
        return ResponseEntity
                .badRequest()
                .body(
                        error
                );
    }
}