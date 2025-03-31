package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseGetStudentDTO;
import com.mvo.edublockapi.dto.StudentDTO;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;
import com.mvo.edublockapi.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/students/")
public class StudentsRestControllerV1 {
    private final StudentService service;

    @PostMapping
    public StudentDTO save(@RequestBody StudentTransientDTO studentTransientDTO) {
        return service.save(studentTransientDTO);
    }

    @GetMapping
    public List<ResponseGetStudentDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public ResponseGetStudentDTO getById(@PathVariable Long id) {
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
