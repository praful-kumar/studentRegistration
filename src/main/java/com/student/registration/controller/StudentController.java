package com.student.registration.controller;

import com.student.registration.exception.StudentNotFoundException;
import com.student.registration.model.Qualification;
import com.student.registration.model.Student;
import com.student.registration.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    // Constructor Injection with @Autowired
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }



    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.addStudent(student), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        if (!studentService.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        if (!studentService.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentService.deleteStudent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Student deleted successfully");
    }


    @GetMapping("/{studentId}/qualifications")
    public ResponseEntity<List<Qualification>> getStudentQualifications(@PathVariable Long studentId) {
        List<Qualification> qualifications = studentService.getQualificationsByStudentId(studentId);
        return ResponseEntity.ok(qualifications);
    }
}
