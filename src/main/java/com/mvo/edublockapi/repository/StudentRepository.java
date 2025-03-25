package com.mvo.edublockapi.repository;

import com.mvo.edublockapi.entity.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "student with courses")
    List<Student> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "student with courses")
    Optional<Student> findById(Long id);
}
