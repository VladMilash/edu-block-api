package com.mvo.edublockapi.repository;

import com.mvo.edublockapi.entity.Department;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "department with headOfDepartment")
    Department findDepartmentById(UUID id);
}
