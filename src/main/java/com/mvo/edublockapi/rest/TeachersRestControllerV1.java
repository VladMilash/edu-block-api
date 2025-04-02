package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.ResponseGetTeacherDTO;
import com.mvo.edublockapi.dto.requestdto.TeacherTransientDTO;
import com.mvo.edublockapi.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/teachers")
public class TeachersRestControllerV1 {
    private final TeacherService service;

    @PostMapping
    ResponseGetTeacherDTO save(@RequestBody TeacherTransientDTO teacherTransientDTO) {
        return service.save(teacherTransientDTO);
    }
}
