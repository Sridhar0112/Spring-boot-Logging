package com.sridhar.springboot.Exception;

import com.sridhar.springboot.Dto.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<?> handleStudentException(
            StudentException ex){
        return ResponseEntity.status(400).body(
                new StudentDto.ErrorResponse(
                        ex.getMessage(),
                        LocalTime.now(),
                        "Bad Request"
                )
        );
    }
}
