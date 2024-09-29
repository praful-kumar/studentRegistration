package com.student.registration.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.student.registration.model.Student;
import com.student.registration.model.Qualification;
import com.student.registration.repository.StudentRepository;
import com.student.registration.service.StudentService;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    // for adding a new student
    @Test
    public void testAddStudent() {
        Student student = new Student();
        student.setName("Praful");
        student.setAge(21);

        List<Qualification> qualifications = new ArrayList<>();
        Qualification qualification = new Qualification();
        qualification.setId(1L);  // Set the existing qualification ID
        qualification.setDegree("B.Tech");
        qualification.setInstitution("Rgpv University");
        qualifications.add(qualification);

        student.setQualifications(qualifications);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student createdStudent = studentService.addStudent(student);


        assertEquals(1, createdStudent.getQualifications().size());
        Qualification createdQualification = createdStudent.getQualifications().get(0);
        assertEquals(1L, createdQualification.getId().longValue());
        assertEquals("B.Tech", createdQualification.getDegree());
        assertEquals("Rgpv University", createdQualification.getInstitution());


        verify(studentRepository, times(1)).save(any(Student.class));
    }

    // For updating an existing student
    @Test
    public void testUpdateStudent() {
        Long studentId = 1L;

        Student existingStudent = new Student();
        existingStudent.setId(studentId);
        existingStudent.setName("Praful");

        List<Qualification> qualifications = new ArrayList<>();
        Qualification qualification = new Qualification();
        qualification.setId(1L);  // Set the existing qualification ID
        qualification.setDegree("B.Tech");
        qualification.setInstitution("Rgpv University");
        qualifications.add(qualification);

        existingStudent.setQualifications(qualifications);


        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(existingStudent);


        Student updatedStudent = new Student();
        updatedStudent.setName("Praful");

        List<Qualification> updatedQualifications = new ArrayList<>();
        Qualification updatedQualification = new Qualification();
        updatedQualification.setId(1L);  // Retain the same qualification ID
        updatedQualification.setDegree("M.Sc.");
        updatedQualification.setInstitution("ABC University");
        updatedQualifications.add(updatedQualification);

        updatedStudent.setQualifications(updatedQualifications);


        Student result = studentService.updateStudent(studentId, updatedStudent);

        // Assertions
        assertEquals("Praful", result.getName());
        assertEquals(1, result.getQualifications().size());
        assertEquals("M.Sc.", result.getQualifications().get(0).getDegree());
        assertEquals("ABC University", result.getQualifications().get(0).getInstitution());

        // Verify repository interactions
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).save(existingStudent);
    }
}
