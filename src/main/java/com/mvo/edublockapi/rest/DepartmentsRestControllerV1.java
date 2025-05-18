package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseDepartmentDTO;
import com.mvo.edublockapi.dto.requestdto.DepartmentTransientDTO;
import com.mvo.edublockapi.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/departments/")
public class DepartmentsRestControllerV1 {
    private final DepartmentService service;

    @PostMapping
    public ResponseEntity<ResponseDepartmentDTO> saveDepartment(@Validated @RequestBody DepartmentTransientDTO departmentTransientDTO,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        ResponseDepartmentDTO responseDepartmentDTO = service.save(departmentTransientDTO);
        URI location = uriComponentsBuilder
            .path("api/v1/departments/{id}")
            .buildAndExpand(responseDepartmentDTO.id())
            .toUri();
        return ResponseEntity
            .created(location)
            .body(responseDepartmentDTO);
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
    public ResponseDepartmentDTO update(@PathVariable Long id, @Validated @RequestBody DepartmentTransientDTO departmentTransientDTO) {
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
