package com.sridhar.springboot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {
        @Id
        private long ID;
        private String name;
        private String course;

        public Student() {
        }

        public Student(int ID, String name, String course) {
            this.ID = ID;
            this.name = name;
            this.course = course;
        }

        public long getID() {
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
