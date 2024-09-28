
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



//    @Lob
//    private byte[] photo;
//
//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
//    private List<DocumentAttachment> documents;

    // Getters and setters

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getFatherName() {
//        return fatherName;
//    }
//
//    public void setFatherName(String fatherName) {
//        this.fatherName = fatherName;
//    }
//
//    public String getMotherName() {
//        return motherName;
//    }
//
//    public void setMotherName(String motherName) {
//        this.motherName = motherName;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public List<Qualification> getQualifications() {
//        return qualifications;
//    }
//
////    public void setQualifications(List<Qualification> qualifications) {
////        this.qualifications = qualifications;
////    }
//// Corrected setQualifications to avoid orphan collection issue
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
