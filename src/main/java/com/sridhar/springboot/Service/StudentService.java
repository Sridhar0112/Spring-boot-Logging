package com.sridhar.springboot.Service;

import com.sridhar.springboot.Dto.StudentDto;
import com.sridhar.springboot.Exception.StudentException;
import com.sridhar.springboot.Repositary.StudentRepository;
import com.sridhar.springboot.models.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }

    public Student AddStudent(StudentDto.StudentRequest student){
        if(student==null || student.getCourse()==null || student.getName()==null){
            throw new StudentException("Student details cannot be empty");
        }
        Student s= new Student();
        s.setCourse(student.getCourse());
        s.setID(student.getID());
        s.setName(student.getName());
        return studentRepository.save(s);
    }

    public List<Student> getStudents(){
        try{
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
