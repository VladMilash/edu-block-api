package com.mvo.edublockapi.repository;

import com.mvo.edublockapi.entity.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    List<Student> findAll();


    @Query("""
        SELECT DISTINCT s FROM Student s
        LEFT JOIN FETCH s.courses c
        LEFT JOIN FETCH c.teacher t       
        LEFT JOIN FETCH t.department d
        LEFT JOIN FETCH d.headOfDepartment h
        WHERE s.id = :id       
        """)
    Optional<Student> findById(Long id);
}
