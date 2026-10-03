package com.example.practice1.repository;

import com.example.practice1.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    List<Student> findAll();
    Optional<Student> findById(Long id);
    Optional<Student> findByUniId(String uniId);
    Student save(Student student);
    boolean deleteById(Long id);
}