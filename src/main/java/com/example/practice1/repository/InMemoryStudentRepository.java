package com.example.practice1.repository;

import com.example.practice1.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryStudentRepository implements StudentRepository {

    private final Map<Long, Student> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    @Override
    public List<Student> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Student> findByUniId(String uniId) {
        return store.values().stream()
                .filter(s -> s.uniId().equals(uniId))
                .findFirst();
    }

    @Override
    public Student save(Student student) {
        Long id = student.id() != null ? student.id() : sequence.incrementAndGet();
        Student saved = new Student(id, student.uniId(), student.fullName());
        store.put(id, saved);
        return saved;
    }

    @Override
    public boolean deleteById(Long id) {
        return store.remove(id) != null;
    }
}