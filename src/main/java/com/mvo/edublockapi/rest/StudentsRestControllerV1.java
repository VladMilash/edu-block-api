package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.CourseShortDTO;
import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseStudentDTO;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;
import com.mvo.edublockapi.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/students/")
public class StudentsRestControllerV1 {
    private final StudentService service;

    @PostMapping
    public ResponseEntity<ResponseStudentDTO> save(@RequestBody StudentTransientDTO studentTransientDTO,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        ResponseStudentDTO responseStudentDTO = service.save(studentTransientDTO);
        URI location = uriComponentsBuilder
            .path("api/v1/students/{id}")
            .buildAndExpand(responseStudentDTO.id())
            .toUri();
        return ResponseEntity
            .created(location)
            .body(responseStudentDTO);
    }

    @GetMapping
    public Page<ResponseStudentDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size) {
        return service.getAll(page, size);
    }

    @GetMapping("{id}")
    public ResponseStudentDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("{id}")
    public ResponseStudentDTO update(@PathVariable Long id, @Validated @RequestBody StudentTransientDTO studentTransientDTO) {
        return service.update(id, studentTransientDTO);
    }

    @DeleteMapping("{id}")
    public DeleteResponseDTO delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("{studentId}/courses/{courseId}")
    public ResponseStudentDTO setRelationWithCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        return service.setRelationWithCourse(studentId,courseId);
    }

    @GetMapping("{id}/courses")
    public Set<CourseShortDTO> getStudentCourses(@PathVariable Long id) {
        return service.getStudentCourses(id);
    }

}
