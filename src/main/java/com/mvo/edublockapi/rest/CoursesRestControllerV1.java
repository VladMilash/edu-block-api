package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.CourseDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
import com.mvo.edublockapi.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/courses/")
public class CoursesRestControllerV1 {
    private final CourseService courseService;

    @PostMapping
    public CourseDTO save(@RequestBody CourseTransientDTO courseTransientDTO) {
        return courseService.save(courseTransientDTO);
    }
}
