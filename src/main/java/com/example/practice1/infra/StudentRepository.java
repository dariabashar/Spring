package com.example.practice1.infra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByUniId(String uniId);

    @Query("select s from StudentEntity s where lower(s.fullName) like lower(concat('%', :name, '%'))")
    Page<StudentEntity> searchByFullName(@Param("name") String name, Pageable pageable);
}
