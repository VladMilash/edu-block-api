package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.entity.Department;
import com.mvo.edublockapi.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/departments/")
public class DepartmentsRestControllerV1 {
    private final DepartmentRepository departmentRepository;

    @GetMapping("{id}")
    public Department findDepartmentById(@PathVariable Long id) {
        return departmentRepository.findDepartmentById(id);
    }

    @PostMapping
    private Department saveDepartment(@RequestBody Department department) {
        return departmentRepository.save(department);
    }
}
