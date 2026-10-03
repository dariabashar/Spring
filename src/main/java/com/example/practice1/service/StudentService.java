package com.example.practice1.service;

import com.example.practice1.audit.IdAuditor;
import com.example.practice1.config.AppIdProperties;
import com.example.practice1.dto.StudentRequest;
import com.example.practice1.dto.StudentResponse;
import com.example.practice1.exception.BadRequestException;
import com.example.practice1.exception.ConflictException;
import com.example.practice1.exception.NotFoundException;
import com.example.practice1.model.Student;
import com.example.practice1.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;
    private final AppIdProperties properties;
    private final IdAuditor auditor;

    public StudentService(StudentRepository repository, AppIdProperties properties, IdAuditor auditor) {
        this.repository = repository;
        this.properties = properties;
        this.auditor = auditor;
    }

    public List<StudentResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public StudentResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public StudentResponse create(StudentRequest request) {
        checkLength(request.uniId());
        repository.findByUniId(request.uniId()).ifPresent(s -> {
            throw new ConflictException("uniId already exists: " + request.uniId());
        });
        Student saved = repository.save(new Student(null, request.uniId(), request.fullName()));
        auditor.audit(saved.uniId());
        return toResponse(saved);
    }

    public StudentResponse update(Long id, StudentRequest request) {
        getOrThrow(id);
        checkLength(request.uniId());
        repository.findByUniId(request.uniId())
                .filter(s -> !s.id().equals(id))
                .ifPresent(s -> {
                    throw new ConflictException("uniId already exists: " + request.uniId());
                });
        Student saved = repository.save(new Student(id, request.uniId(), request.fullName()));
        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Student " + id + " not found");
        }
    }

    private Student getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student " + id + " not found"));
    }

    private void checkLength(String uniId) {
        if (uniId.length() < properties.minIdLength() || uniId.length() > properties.maxIdLength()) {
            throw new BadRequestException("uniId length must be between "
                    + properties.minIdLength() + " and " + properties.maxIdLength());
        }
    }

    private StudentResponse toResponse(Student s) {
        return new StudentResponse(s.id(), s.uniId(), s.fullName());
    }
}