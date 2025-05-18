package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseCoursesDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
import com.mvo.edublockapi.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/courses/")
public class CoursesRestControllerV1 {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ResponseCoursesDTO> save(@Validated @RequestBody CourseTransientDTO courseTransientDTO,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        ResponseCoursesDTO responseCoursesDTO = courseService.save(courseTransientDTO);
        URI location = uriComponentsBuilder
            .path("api/v1/courses/{id}")
            .buildAndExpand(responseCoursesDTO.id())
            .toUri();
        return ResponseEntity
            .created(location)
            .body(responseCoursesDTO);
    }

    @GetMapping
    public Page<ResponseCoursesDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size) {
        return courseService.getAll(page, size);
    }

    @GetMapping("{id}")
    public ResponseCoursesDTO getById(@PathVariable long id) {
        return courseService.getById(id);
    }

    @PutMapping("{id}")
    public ResponseCoursesDTO update(@PathVariable Long id, @Validated @RequestBody CourseTransientDTO courseTransientDTO) {
        return courseService.update(id, courseTransientDTO);
    }

    @DeleteMapping("{id}")
    public DeleteResponseDTO delete(@PathVariable Long id) {
        return courseService.delete(id);
    }
}
