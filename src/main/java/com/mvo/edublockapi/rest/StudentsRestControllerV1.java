package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.CourseShortDTO;
import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseStudentDTO;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;
import com.mvo.edublockapi.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
        summary = "Создание студента",
        description = "Позволяет создать нового студента"
    )
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

    @Operation(
        summary = "Получение всех студентов",
        description = "Позволяет получить всех студентов"
    )
    @GetMapping
    public Page<ResponseStudentDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size) {
        return service.getAll(page, size);
    }

    @Operation(
        summary = "Получение студента по id",
        description = "Позволяет получить студента по id"
    )
    @GetMapping("{id}")
    public ResponseStudentDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(
        summary = "Обновление студента по id",
        description = "Позволяет обновить студента по id"
    )
    @PutMapping("{id}")
    public ResponseStudentDTO update(@PathVariable Long id, @Validated @RequestBody StudentTransientDTO studentTransientDTO) {
        return service.update(id, studentTransientDTO);
    }

    @Operation(
        summary = "Удаление студента по id",
        description = "Позволяет удалить студента по id"
    )
    @DeleteMapping("{id}")
    public DeleteResponseDTO delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @Operation(
        summary = "Установление связи студент-курс",
        description = "Позволяет установить связь студент-курс"
    )
    @PostMapping("{studentId}/courses/{courseId}")
    public ResponseStudentDTO setRelationWithCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        return service.setRelationWithCourse(studentId,courseId);
    }

    @Operation(
        summary = "Получить все курсы студента по id ",
        description = "Позволяет установить получить все курсы стундента по его id"
    )
    @GetMapping("{id}/courses")
    public Set<CourseShortDTO> getStudentCourses(@PathVariable Long id) {
        return service.getStudentCourses(id);
    }

}
