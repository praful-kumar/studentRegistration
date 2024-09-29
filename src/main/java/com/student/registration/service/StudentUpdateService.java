package com.student.registration.service;

import com.student.registration.repository.QualificationRepository;
import com.student.registration.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Slf4j
@Service
public class StudentUpdateService {

    private final StudentRepository studentRepository;
    private final QualificationRepository qualificationRepository;

    @Autowired
    public StudentUpdateService(StudentRepository studentRepository, QualificationRepository qualificationRepository) {
        this.studentRepository = studentRepository;
        this.qualificationRepository = qualificationRepository;
    }
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateStudentRecords() {
        log.info("Running scheduled task to update employee records...");
        studentRepository.findAll().forEach(student -> {
            String formattedDateTime = LocalDateTime.now().format(FORMATTER);
            student.setLastUpdated(formattedDateTime);
            studentRepository.save(student);
            log.info("Updated student record :{}" , student.getId());
        });
    }
}
