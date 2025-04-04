package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseGetTeacherDTO;
import com.mvo.edublockapi.dto.requestdto.TeacherTransientDTO;
import com.mvo.edublockapi.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/teachers")
public class TeachersRestControllerV1 {
    private final TeacherService service;

    @PostMapping
    public ResponseGetTeacherDTO save(@RequestBody TeacherTransientDTO teacherTransientDTO) {
        return service.save(teacherTransientDTO);
    }

    @GetMapping("{id}")
    public ResponseGetTeacherDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<ResponseGetTeacherDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("{id}")
    public ResponseGetTeacherDTO update(@PathVariable Long id, @RequestBody TeacherTransientDTO teacherTransientDTO) {
        return service.update(id,teacherTransientDTO);
    }

    @DeleteMapping("{id}")
    public DeleteResponseDTO delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
