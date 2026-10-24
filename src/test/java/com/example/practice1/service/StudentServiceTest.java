package com.example.practice1.service;

import com.example.practice1.audit.IdAuditor;
import com.example.practice1.config.AppIdProperties;
import com.example.practice1.dto.StudentRequest;
import com.example.practice1.dto.StudentResponse;
import com.example.practice1.entity.StudentEntity;
import com.example.practice1.exception.BadRequestException;
import com.example.practice1.exception.ConflictException;
import com.example.practice1.exception.NotFoundException;
import com.example.practice1.mapper.StudentMapper;
import com.example.practice1.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;
    @Mock
    private AppIdProperties properties;
    @Mock
    private IdAuditor auditor;

    private StudentService service;

    @BeforeEach
    void setUp() {
        lenient().when(properties.minIdLength()).thenReturn(9);
        lenient().when(properties.maxIdLength()).thenReturn(9);
        service = new StudentService(repository, properties, auditor, new StudentMapper());
    }

    @Test
    void createSavesStudent() {
        StudentRequest request = new StudentRequest("23B030286", "Aida Nurlanova");
        when(repository.findByUniId("23B030286")).thenReturn(Optional.empty());
        when(repository.save(any(StudentEntity.class)))
                .thenReturn(new StudentEntity(1L, "23B030286", "Aida Nurlanova"));

        StudentResponse response = service.create(request);

        assertEquals(1L, response.id());
        assertEquals("23B030286", response.uniId());
        verify(auditor).audit("23B030286");
    }

    @Test
    void createRejectsDuplicateUniId() {
        StudentRequest request = new StudentRequest("23B030286", "Aida Nurlanova");
        when(repository.findByUniId("23B030286"))
                .thenReturn(Optional.of(new StudentEntity(2L, "23B030286", "Other")));

        assertThrows(ConflictException.class, () -> service.create(request));
    }

    @Test
    void createRejectsShortUniId() {
        StudentRequest request = new StudentRequest("123", "Aida Nurlanova");

        assertThrows(BadRequestException.class, () -> service.create(request));
    }

    @Test
    void findByIdThrowsWhenMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(99L));
    }
}
