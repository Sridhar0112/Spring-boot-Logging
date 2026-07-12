package com.sridhar.springboot.Controller;

import com.sridhar.springboot.Dto.StudentDto;
import com.sridhar.springboot.Exception.StudentException;
import com.sridhar.springboot.Service.StudentService;
import com.sridhar.springboot.models.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {
    StudentService studentService;
    public StudentController(StudentService s){
        studentService=s;
    }

    @PostMapping("/student/add")
    public ResponseEntity<StudentDto.StudentResponse> addStudent(@RequestBody StudentDto.StudentRequest student){
        Student s=studentService.AddStudent(student);
        return ResponseEntity.status(200).body(new StudentDto.StudentResponse("Student added successfully", s.getName()));

    }
    @GetMapping("/student/getstudent")
    public ResponseEntity<Map<String,List<?>>>getStudent(){
           List<Student> response=studentService.getStudents();
           return ResponseEntity.ok().body(Map.of("Students",response));

    }
    @GetMapping("/student/getstudent/{id}")
    public ResponseEntity<?>getStudentById(@PathVariable Long id){
            Student response=studentService.getStudentsById(id);
            return ResponseEntity.ok().body(Map.of("Student",response));

    }
}
