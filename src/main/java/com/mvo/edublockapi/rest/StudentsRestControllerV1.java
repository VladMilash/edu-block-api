package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.CourseShortDTO;
import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseStudentDTO;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;
import com.mvo.edublockapi.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/students/")
public class StudentsRestControllerV1 {
    private final StudentService service;

    @PostMapping
    public ResponseStudentDTO save(@RequestBody StudentTransientDTO studentTransientDTO) {
        return service.save(studentTransientDTO);
    }

    @GetMapping
    public List<ResponseStudentDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public ResponseStudentDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("{id}")
    public ResponseStudentDTO update(@PathVariable Long id, @RequestBody StudentTransientDTO studentTransientDTO) {
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
