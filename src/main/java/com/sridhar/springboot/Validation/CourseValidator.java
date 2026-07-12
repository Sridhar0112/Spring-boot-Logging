package com.sridhar.springboot.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class CourseValidator implements ConstraintValidator<ValidCourse,String> {
    public static final List<String> AllowedCourses=List.of(
            "JAVA",
            "SPRING BOOT",
            "H2",
            "SERVLET",
            "JDBC"
    );
    @Override
    public boolean isValid(String course, ConstraintValidatorContext context) {
        if(course==null){
            return true;
        }
        return AllowedCourses.contains(course.toUpperCase());

    }
}