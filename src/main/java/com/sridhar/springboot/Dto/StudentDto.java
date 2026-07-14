package com.sridhar.springboot.Dto;


import com.sridhar.springboot.Validation.ValidCourse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class StudentDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StudentResponse{
        String message;
        String name;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentRequest{
        private Long ID;
        @NotBlank(message = "student name is must required")
        @Size(max = 25,min = 3,message = "Name must contain 3 to 20 characters")
        private String name;
        @NotBlank(message = "course must required")
        @ValidCourse
        private String course;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorResponse{
        String errormsg;
        LocalTime localTime;
        String Details;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApiErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String message;
        private String path;
        private Map<?, ?> errors;

    }
}