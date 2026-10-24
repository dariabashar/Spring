package com.example.practice1.infra;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uni_id", nullable = false, unique = true, length = 9)
    private String uniId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    protected StudentEntity() {
    }

    public StudentEntity(Long id, String uniId, String fullName) {
        this.id = id;
        this.uniId = uniId;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public String getUniId() {
        return uniId;
    }

    public String getFullName() {
        return fullName;
    }
}
