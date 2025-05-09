package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseCoursesDTO;
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
    public ResponseCoursesDTO save(@RequestBody CourseTransientDTO courseTransientDTO) {
        return courseService.save(courseTransientDTO);
    }

    @GetMapping
    public List<ResponseCoursesDTO> getAll() {
        return courseService.getAll();
    }

    @GetMapping("{id}")
    public ResponseCoursesDTO getById(@PathVariable long id) {
        return courseService.getById(id);
    }

    @PutMapping("{id}")
    public ResponseCoursesDTO update(@PathVariable Long id, @RequestBody CourseTransientDTO courseTransientDTO) {
        return courseService.update(id, courseTransientDTO);
    }

    @DeleteMapping("{id}")
    public DeleteResponseDTO delete(@PathVariable Long id) {
        return courseService.delete(id);
    }
}
