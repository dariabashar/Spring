package com.example.practice1.service;

import com.example.practice1.audit.IdAuditor;
import com.example.practice1.config.AppIdProperties;
import com.example.practice1.domain.Student;
import com.example.practice1.dto.StudentRequest;
import com.example.practice1.dto.StudentResponse;
import com.example.practice1.exception.BadRequestException;
import com.example.practice1.exception.ConflictException;
import com.example.practice1.exception.NotFoundException;
import com.example.practice1.mapper.StudentMapper;
import com.example.practice1.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository repository;
    private final AppIdProperties properties;
    private final IdAuditor auditor;
    private final StudentMapper mapper;

    public StudentService(StudentRepository repository,
                          AppIdProperties properties,
                          IdAuditor auditor,
                          StudentMapper mapper) {
        this.repository = repository;
        this.properties = properties;
        this.auditor = auditor;
        this.mapper = mapper;
    }

    public Page<StudentResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDomain).map(mapper::toResponse);
    }

    public Page<StudentResponse> search(String name, Pageable pageable) {
        return repository.searchByFullName(name, pageable).map(mapper::toDomain).map(mapper::toResponse);
    }

    public StudentResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    @Transactional
    public StudentResponse create(StudentRequest request) {
        Student student = mapper.toDomain(request);
        checkLength(student.uniId());
        repository.findByUniId(student.uniId()).ifPresent(existing -> {
            throw new ConflictException("uniId already exists: " + student.uniId());
        });
        Student saved = mapper.toDomain(repository.save(mapper.toEntity(student)));
        auditor.audit(saved.uniId());
        return mapper.toResponse(saved);
    }

    @Transactional
    public StudentResponse update(Long id, StudentRequest request) {
        getOrThrow(id);
        Student student = mapper.toDomain(id, request);
        checkLength(student.uniId());
        repository.findByUniId(student.uniId())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ConflictException("uniId already exists: " + student.uniId());
                });
        return mapper.toResponse(mapper.toDomain(repository.save(mapper.toEntity(student))));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Student " + id + " not found");
        }
        repository.deleteById(id);
    }

    private Student getOrThrow(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Student " + id + " not found"));
    }

    private void checkLength(String uniId) {
        if (uniId.length() < properties.minIdLength() || uniId.length() > properties.maxIdLength()) {
            throw new BadRequestException("uniId length must be between "
                    + properties.minIdLength() + " and " + properties.maxIdLength());
        }
    }
}
