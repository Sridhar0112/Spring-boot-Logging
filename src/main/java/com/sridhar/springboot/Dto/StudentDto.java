package com.sridhar.springboot.Dto;


import java.time.LocalTime;

public class StudentDto {
    public static class StudentResponse{
        String message;
        String name;

        public StudentResponse(String message, String name) {
            this.message = message;
            this.name = name;
        }

        public StudentResponse() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class StudentRequest{
        private int ID;
        private String name;
        private String course;

        public StudentRequest() {
        }

        public StudentRequest(int ID, String name, String course) {
            this.ID = ID;
            this.name = name;
            this.course = course;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }
    }
    public static class ErrorResponse{
        String errormsg;
        LocalTime localTime;
        String Details;

        public ErrorResponse() {
        }

        public ErrorResponse(String errormsg, LocalTime localTime, String details) {
            this.errormsg = errormsg;
            this.localTime = localTime;
            Details = details;
        }

        public String getErrormsg() {
            return errormsg;
        }

        public void setErrormsg(String errormsg) {
            this.errormsg = errormsg;
        }

        public LocalTime getLocalTime() {
            return localTime;
        }

        public void setLocalTime(LocalTime localTime) {
            this.localTime = localTime;
        }

        public String getDetails() {
            return Details;
        }

        public void setDetails(String details) {
            Details = details;
        }
    }
}
