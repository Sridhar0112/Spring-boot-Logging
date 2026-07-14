package com.sridhar.springboot.logging.util;

import com.sridhar.springboot.Dto.StudentDto;
import com.sridhar.springboot.models.Student;

public final class LoggingMaskUtil {

    private LoggingMaskUtil() {
    }

    /**
     * Mask Student Entity
     */
    public static String mask(Student student) {

        if (student == null) {
            return "Student{null}";
        }

        return String.format(
                "Student{id=%d, name=%s, email=%s, phone=%s, course=%s}",
                student.getID(),
                student.getName(),


                maskEmail(student.getEmail()),
                maskPhone(student.getPhone()),
                student.getCourse()
        );
    }

    /**
     * Mask Student Request DTO
     */
    public static String mask(StudentDto.StudentRequest student) {

        if (student == null) {
            return "StudentRequest{null}";
        }

        return String.format(
                "StudentRequest{id=%d, name=%s, email=%s, phone=%s, course=%s}",
                student.getID(),
                student.getName(),
                maskEmail(student.getEmail()),
                maskPhone(student.getPhone()),
                student.getCourse()
        );
    }


    /**
     * Mask Email
     * sridhar@gmail.com
     * ->
     * s******@gmail.com
     */
    public static String maskEmail(String email) {
        if (email == null || email.isBlank()) {
            return "N/A";
        }
        int atIndex = email.indexOf("@");
        if (atIndex <= 1) {
            return "*****";
        }
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex);
        return username.charAt(0)
                + "*".repeat(username.length() - 1)
                + domain;
    }

    /**
     * Mask Phone
     * 9876543210
     * ->
     * 98******10
     */
    public static String maskPhone(String phone) {
        if (phone == null || phone.length() != 10) {
            return "**********";
        }
        return phone.substring(0, 2) + "******" + phone.substring(8);
    }
}