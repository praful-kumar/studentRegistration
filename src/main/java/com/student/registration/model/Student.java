
package com.student.registration.model;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
@Data
@ToString(exclude = "qualifications")
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String gender;
    private String fatherName;
    private String motherName;
    private String address;
    private String lastUpdated;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Qualification> qualifications;


    public void setQualifications(List<Qualification> newQualifications) {
        // Clear existing qualifications if needed
        if (this.qualifications != null) {
            this.qualifications.clear();
            this.qualifications.addAll(newQualifications);
        } else {
            this.qualifications = newQualifications;
        }

        // Set the student reference in qualifications
        for (Qualification qualification : this.qualifications) {
            qualification.setStudent(this);
        }
    }
}
