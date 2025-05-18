package com.mvo.edublockapi.repository;

import com.mvo.edublockapi.entity.Student;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("""
        SELECT DISTINCT s FROM Student s
        LEFT JOIN FETCH s.courses c
        LEFT JOIN FETCH c.teacher t       
        LEFT JOIN FETCH t.department d
        LEFT JOIN FETCH d.headOfDepartment h
        """)
    Page<Student> findAll(Pageable pageable);


    @Query("""
        SELECT DISTINCT s FROM Student s
        LEFT JOIN FETCH s.courses c
        LEFT JOIN FETCH c.teacher t       
        LEFT JOIN FETCH t.department d
        LEFT JOIN FETCH d.headOfDepartment h
        WHERE s.id = :id       
        """)
    Optional<Student> findById(Long id);


    @Query("""
        SELECT DISTINCT s FROM Student s
        LEFT JOIN FETCH s.courses c
        LEFT JOIN FETCH c.teacher t       
        LEFT JOIN FETCH t.department d
        LEFT JOIN FETCH d.headOfDepartment h
        WHERE s.email = :email       
        """)
    Student findByEmail(String email);

    boolean existsByEmail(@NotNull String email);

}
