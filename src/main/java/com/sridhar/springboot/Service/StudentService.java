package com.sridhar.springboot.Service;

import com.sridhar.springboot.Dto.StudentDto;
import com.sridhar.springboot.Exception.StudentException;
import com.sridhar.springboot.Repository.StudentRepository;
import com.sridhar.springboot.logging.util.LoggingMaskUtil;
import com.sridhar.springboot.models.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Student AddStudent(StudentDto.StudentRequest student){
        log.info("Student creation started. {}", LoggingMaskUtil.mask(student));
        if(student==null || student.getCourse()==null || student.getName()==null){
            log.warn("Student creation failed. Mandatory fields missing");
            throw new StudentException("Student details cannot be empty");
        }
        Student s= new Student();
        s.setCourse(student.getCourse());
        s.setID(student.getID());
        s.setName(student.getName());
        s.setEmail(student.getEmail());
        s.setPhone(student.getPhone());
        Student savedstudent= studentRepository.save(s);
        log.info("Student created successfully. {}", LoggingMaskUtil.mask(savedstudent));
        return savedstudent;
    }

    public List<Student> getStudents(){
        try{
            log.info("Fetching all students");
            return studentRepository.findAll();
        }
        catch(Exception e){
            throw new StudentException(e.getMessage());
        }
    }

    public Student getStudentsById(long id) {
        return studentRepository.findById(id).orElseThrow(()-> new StudentException("Student not found for Id: "+ id));
    }
}