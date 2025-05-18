package com.mvo.edublockapi.repository;

import com.mvo.edublockapi.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findDepartmentById(Long id);

    @Query("""
        SELECT DISTINCT d FROM Department d
        LEFT JOIN FETCH d.headOfDepartment h                      
        """)
    Page<Department> findAll(Pageable pageable);

    @Query("""
        SELECT DISTINCT d FROM Department d
        LEFT JOIN FETCH d.headOfDepartment h
        WHERE d.id = :id                            
        """)
    Optional<Department> findById(Long id);
}
