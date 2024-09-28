package com.student.registration.repository;

import com.student.registration.model.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long> {

    // Fetch qualifications by studentId
    List<Qualification> findByStudentId(Long studentId);
}
