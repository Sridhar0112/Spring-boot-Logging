package com.sridhar.springboot.Validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CourseValidator.class)
public @interface ValidCourse {
    String message() default "Invalid Course";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };


}