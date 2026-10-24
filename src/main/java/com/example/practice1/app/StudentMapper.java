package com.example.practice1.app;

import com.example.practice1.domain.Student;
import com.example.practice1.web.dto.StudentRequest;
import com.example.practice1.web.dto.StudentResponse;
import com.example.practice1.infra.StudentEntity;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Student toDomain(StudentRequest request) {
        return new Student(null, request.uniId(), request.fullName());
    }

    public Student toDomain(Long id, StudentRequest request) {
        return new Student(id, request.uniId(), request.fullName());
    }

    public Student toDomain(StudentEntity entity) {
        return new Student(entity.getId(), entity.getUniId(), entity.getFullName());
    }

    public StudentEntity toEntity(Student student) {
        return new StudentEntity(student.id(), student.uniId(), student.fullName());
    }

    public StudentResponse toResponse(Student student) {
        return new StudentResponse(student.id(), student.uniId(), student.fullName());
    }
}
