package com.student.registration.controller;

import com.student.registration.model.Student;
import com.student.registration.model.Qualification;
import com.student.registration.service.StudentService;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    // Test Case 1: Get Student by ID
    @Test
    public void testGetStudentById() throws Exception {
        // Mock student data
        Student student = new Student();
        student.setId(1L);
        student.setName("Rahul");
        student.setAge(22);
        student.setGender("Male");

        // Mock qualification data
        List<Qualification> qualifications = new ArrayList<>();
        Qualification qualification = new Qualification(1L, "B.Tech", "RGPV University", "2021", student);
        qualifications.add(qualification);
        student.setQualifications(qualifications);

        // Mock service call
        when(studentService.getStudentById(1L)).thenReturn(student);

        // Perform GET request
        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rahul"))
                .andExpect(jsonPath("$.age").value(22))
                .andExpect(jsonPath("$.qualifications[0].degree").value("B.Tech"))
                .andExpect(jsonPath("$.qualifications[0].institution").value("RGPV University"));
    }

    // Test Case 2: Add a new Student
    @Test
    public void testAddStudent() throws Exception {
        // Mock student data
        Student student = new Student();
        student.setName("Priya");
        student.setAge(21);
        student.setGender("Female");

        // Mock qualification data
        List<Qualification> qualifications = new ArrayList<>();
        Qualification qualification = new Qualification(1L, "MBA", "ABC University", "2023", student);
        qualifications.add(qualification);
        student.setQualifications(qualifications);

        // Mock service call for adding student
        when(studentService.addStudent(any(Student.class))).thenReturn(student);

        // Perform POST request
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\":\"Priya\", \"age\":21, \"gender\":\"Female\", " +
                                "\"qualifications\": [{ \"degree\":\"MBA\", \"institution\":\"ABC University\", \"yearOfPassing\":\"2023\" }] }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Priya"))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.qualifications[0].degree").value("MBA"))
                .andExpect(jsonPath("$.qualifications[0].institution").value("ABC University"));
    }
}
