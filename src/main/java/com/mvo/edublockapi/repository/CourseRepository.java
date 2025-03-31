package com.mvo.edublockapi.repository;

import com.mvo.edublockapi.entity.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("""
        SELECT DISTINCT c FROM Course c
        LEFT JOIN FETCH c.teacher t
        LEFT JOIN FETCH t.department d
        LEFT JOIN FETCH d.headOfDepartment h     
        LEFT JOIN FETCH c.students s                     
        """)
    List<Course> findAll();

    @Query("""
        SELECT DISTINCT c FROM Course c
        LEFT JOIN FETCH c.teacher t
        LEFT JOIN FETCH t.department d
        LEFT JOIN FETCH d.headOfDepartment h     
        LEFT JOIN FETCH c.students s   
        WHERE c.id = :id                          
        """)
    Optional<Course> findById(Long id);
}

