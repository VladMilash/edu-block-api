package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseDepartmentDTO;
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
    public ResponseDepartmentDTO saveDepartment(@RequestBody DepartmentTransientDTO departmentTransientDTO) {
        return service.save(departmentTransientDTO);
    }

    @GetMapping
    public List<ResponseDepartmentDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public ResponseDepartmentDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("{id}")
    public ResponseDepartmentDTO update(@PathVariable Long id, @RequestBody DepartmentTransientDTO departmentTransientDTO) {
        return service.update(id, departmentTransientDTO);
    }

    @DeleteMapping("{id}")
    public DeleteResponseDTO delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("{departmentId}/teacher/{teacherId}")
    public ResponseDepartmentDTO setRelationWitTeacher(@PathVariable Long departmentId, @PathVariable Long teacherId) {
        return service.setRelationWithTeacher(departmentId, teacherId);
    }

}
