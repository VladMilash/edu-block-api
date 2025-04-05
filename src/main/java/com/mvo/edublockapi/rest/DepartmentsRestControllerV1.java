package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseGetDepartmentDTO;
import com.mvo.edublockapi.dto.requestdto.DepartmentTransientDTO;
import com.mvo.edublockapi.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/departments/")
public class DepartmentsRestControllerV1 {
    private final DepartmentService service;

    @PostMapping
    public ResponseGetDepartmentDTO saveDepartment(@RequestBody DepartmentTransientDTO departmentTransientDTO) {
        return service.save(departmentTransientDTO);
    }

    @GetMapping
    public List<ResponseGetDepartmentDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public ResponseGetDepartmentDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("{id}")
    public ResponseGetDepartmentDTO update(@PathVariable Long id, @RequestBody DepartmentTransientDTO departmentTransientDTO) {
        return service.update(id, departmentTransientDTO);
    }

    @DeleteMapping("{id}")
    public DeleteResponseDTO delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("{departmentId}/teacher/{teacherId}")
    public ResponseGetDepartmentDTO setRelationWitTeacher(@PathVariable Long departmentId, @PathVariable Long teacherId) {
        return service.setRelationWithTeacher(departmentId, teacherId);
    }

}
