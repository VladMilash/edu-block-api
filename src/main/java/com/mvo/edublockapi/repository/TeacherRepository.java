package com.mvo.edublockapi.repository;

import com.mvo.edublockapi.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("""
        SELECT DISTINCT t FROM Teacher t
        LEFT JOIN FETCH t.courses c 
        LEFT JOIN FETCH c.teacher c_t
        LEFT JOIN FETCH c_t.department                
        LEFT JOIN FETCH t.department
        WHERE t.id = :id                   
        """)
    Optional<Teacher> findTeacherById(Long id);

    @Query("""
        SELECT DISTINCT t FROM Teacher t
        LEFT JOIN FETCH t.courses c 
        LEFT JOIN FETCH c.teacher c_t
        LEFT JOIN FETCH c_t.department                
        LEFT JOIN FETCH t.department                 
        """)
    List<Teacher> findAll();
}
