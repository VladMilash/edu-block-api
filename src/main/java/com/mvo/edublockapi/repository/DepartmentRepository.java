package com.mvo.edublockapi.repository;

import com.mvo.edublockapi.entity.Department;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "department with headOfDepartment")
    Department findDepartmentById(Long id);

}
