package com.sridhar.springboot.Controller;

import com.sridhar.springboot.Dto.StudentDto;
import com.sridhar.springboot.Exception.StudentException;
import com.sridhar.springboot.Service.StudentService;
import com.sridhar.springboot.models.Student;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class StudentController {
    StudentService studentService;
    public StudentController(StudentService s){
        studentService=s;
    }

    @PostMapping("/student/add")
    public ResponseEntity<StudentDto.StudentResponse> addStudent(@Valid @RequestBody StudentDto.StudentRequest student){
        log.debug("Incoming student request {}",student);
        Student s=studentService.AddStudent(student);
        log.info("Student created successfully. StudentName={}", s.getName());
        return ResponseEntity.status(200).body(new StudentDto.StudentResponse("Student added successfully", s.getName()));
    }

    @GetMapping("/student/getstudent")
    public ResponseEntity<Map<String,List<?>>>getStudent(){
        log.info("GET /student/getstudent request received");
        List<Student> response=studentService.getStudents();
        log.info("Students fetched successfully. Count={}", response.size());
        return ResponseEntity.ok().body(Map.of("Students",response));
    }

    @GetMapping("/student/getstudent/{id}")
    public ResponseEntity<?>getStudentById(@Min(value = 1,message = "ID must be greater than 0") @PathVariable Long id){
        log.info("GET /student/getstudent/{} request received", id);
        Student response=studentService.getStudentsById(id);
        log.info("Student found successfully. Id={}", id);
        return ResponseEntity.ok().body(Map.of("Student",response));
    }
}
