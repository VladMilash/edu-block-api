package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.CourseDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
import com.mvo.edublockapi.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/courses/")
public class CoursesRestControllerV1 {
    private final CourseService courseService;

    @PostMapping
    public CourseDTO save(@RequestBody CourseTransientDTO courseTransientDTO) {
        return courseService.save(courseTransientDTO);
    }

    @GetMapping
    public List<CourseDTO> getAll() {
        return courseService.getAll();
    }
}
