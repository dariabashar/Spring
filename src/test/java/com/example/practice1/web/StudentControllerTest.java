package com.example.practice1.web;

import com.example.practice1.web.dto.StudentRequest;
import com.example.practice1.web.dto.StudentResponse;
import com.example.practice1.exception.NotFoundException;
import com.example.practice1.app.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService service;

    @Test
    void getAllReturnsPage() throws Exception {
        StudentResponse student = new StudentResponse(1L, "23B030286", "Aida Nurlanova");
        when(service.findAll(any())).thenReturn(
                new PageImpl<>(List.of(student), PageRequest.of(0, 20), 1));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].uniId").value("23B030286"));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any(StudentRequest.class)))
                .thenReturn(new StudentResponse(1L, "23B030286", "Aida Nurlanova"));

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"uniId\":\"23B030286\",\"fullName\":\"Aida Nurlanova\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/students/1"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getOneReturns404() throws Exception {
        when(service.findById(eq(99L))).thenThrow(new NotFoundException("Student 99 not found"));

        mockMvc.perform(get("/api/students/99"))
                .andExpect(status().isNotFound());
    }
}
