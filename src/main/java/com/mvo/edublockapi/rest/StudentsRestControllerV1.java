package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.StudentDTO;
import com.mvo.edublockapi.dto.StudentTransientDTO;
import com.mvo.edublockapi.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/students/")
public class StudentsRestControllerV1 {
    private final StudentService service;

    @PostMapping
    public StudentDTO saveStudent(@RequestBody StudentTransientDTO studentTransientDTO) {
        return service.save(studentTransientDTO);
    }

    @GetMapping
    public List<StudentDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public StudentDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("{id}")
    public StudentDTO update(@PathVariable Long id, @RequestBody StudentTransientDTO studentTransientDTO) {
        return service.update(id, studentTransientDTO);
    }

    @DeleteMapping("{id}")
    public DeleteResponseDTO delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
