// src/main/java/com/student/registration/model/Qualification.java
package com.student.registration.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "qualifications")
@Data  // Generates getters, setters, toString, equals, and hashCode
@ToString(exclude = "student")
@NoArgsConstructor  // Generates a no-argument constructor
@AllArgsConstructor // Generates an all-argument constructor
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String degree;
    private String institution;
    private String yearOfPassing;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    private Student student;

}
