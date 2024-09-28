package com.student.registration.service;

import com.student.registration.exception.StudentNotFoundException;
import com.student.registration.model.Student;
import com.student.registration.model.Qualification;
import com.student.registration.repository.QualificationRepository;
import com.student.registration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final QualificationRepository qualificationRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, QualificationRepository qualificationRepository) {
        this.studentRepository = studentRepository;
        this.qualificationRepository = qualificationRepository;
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Student addStudent(Student studentDTO) {
        log.info("Adding a new student: {}" , studentDTO.toString());
        Student student = new Student();
        // Map fields from studentDTO to student
        student.setName(studentDTO.getName());
        student.setAge(studentDTO.getAge());
        student.setGender(studentDTO.getGender());
        student.setFatherName(studentDTO.getFatherName());
        student.setMotherName(studentDTO.getMotherName());
        student.setAddress(studentDTO.getAddress());
        String formattedDateTime = LocalDateTime.now().format(FORMATTER);
        student.setLastUpdated(formattedDateTime);
        // Handle qualifications
        List<Qualification> qualifications = studentDTO.getQualifications().stream()
                .map(dto -> {
                    Qualification qualification = new Qualification();
                    qualification.setDegree(dto.getDegree());
                    qualification.setInstitution(dto.getInstitution());
                    qualification.setYearOfPassing(dto.getYearOfPassing());
                    qualification.setStudent(student);
                    return qualification;
                })
                .collect(Collectors.toList());
        student.setQualifications(qualifications);
        Student savedStudent = studentRepository.save(student);
        log.info("Student added successfully with ID: {}", savedStudent.getId());
        return savedStudent;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        log.info("Fetching qualifications for student ID: " + id);
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Transactional // This is now using the Spring Transactional annotation
    public Student updateStudent(Long id, Student studentDTO) {
        log.info("Updating student with ID: ", id);
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        existingStudent.setName(studentDTO.getName());
        existingStudent.setAge(studentDTO.getAge());
        existingStudent.setGender(studentDTO.getGender());
        existingStudent.setFatherName(studentDTO.getFatherName());
        existingStudent.setMotherName(studentDTO.getMotherName());
        existingStudent.setAddress(studentDTO.getAddress());
        String formattedDateTime = LocalDateTime.now().format(FORMATTER);
        existingStudent.setLastUpdated(formattedDateTime);
        existingStudent.setQualifications(studentDTO.getQualifications());

        Student updatedStudent = studentRepository.save(existingStudent);
        log.info("Student with ID: {} updated successfully", id);
        return updatedStudent;
    }

    public void deleteStudent(Long id) {
         studentRepository.deleteById(id);
        log.info("Student with ID: {} deleted successfully", id);
    }

    public boolean existsById(Long id) {
        return studentRepository.existsById(id);
    }







    // Fetch qualifications by studentId
    public List<Qualification> getQualificationsByStudentId(Long studentId) {
        log.info("Fetching qualifications for student ID: {}", studentId);
        return qualificationRepository.findByStudentId(studentId);
    }
}
