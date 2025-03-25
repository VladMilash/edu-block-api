package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.StudentDTO;
import com.mvo.edublockapi.dto.StudentRegistrationDTO;
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
    public StudentDTO saveStudent(@RequestBody StudentRegistrationDTO studentRegistrationDTO) {
         return service.save(studentRegistrationDTO);
    }

    @GetMapping
    private List<StudentDTO> getAll() {
        return service.getAll();
    }
}
