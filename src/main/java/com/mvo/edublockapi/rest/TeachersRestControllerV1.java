package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseCoursesDTO;
import com.mvo.edublockapi.dto.ResponseTeacherDTO;
import com.mvo.edublockapi.dto.requestdto.TeacherTransientDTO;
import com.mvo.edublockapi.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/teachers")
public class TeachersRestControllerV1 {
    private final TeacherService service;

    @PostMapping
    public ResponseEntity<ResponseTeacherDTO> save(@Validated @RequestBody TeacherTransientDTO teacherTransientDTO,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        ResponseTeacherDTO responseTeacherDTO = service.save(teacherTransientDTO);
        URI location = uriComponentsBuilder
            .path("api/v1/teachers/{id}")
            .buildAndExpand(responseTeacherDTO.id())
            .toUri();
        return ResponseEntity
            .created(location)
            .body(responseTeacherDTO);
    }

    @GetMapping("{id}")
    public ResponseTeacherDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<ResponseTeacherDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("{id}")
    public ResponseTeacherDTO update(@PathVariable Long id, @Validated @RequestBody TeacherTransientDTO teacherTransientDTO) {
        return service.update(id,teacherTransientDTO);
    }

    @DeleteMapping("{id}")
    public DeleteResponseDTO delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("/{teacherId}/courses/{coursesId}")
    public ResponseCoursesDTO setRelationTeacherWithCourse(@PathVariable Long teacherId, @PathVariable Long coursesId) {
        return service.setRelationTeacherWithCourse(teacherId, coursesId);
    }

}
