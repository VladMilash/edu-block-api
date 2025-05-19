package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseCoursesDTO;
import com.mvo.edublockapi.dto.ResponseTeacherDTO;
import com.mvo.edublockapi.dto.requestdto.TeacherTransientDTO;
import com.mvo.edublockapi.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/teachers")
public class TeachersRestControllerV1 {
    private final TeacherService service;

    @Operation(
        summary = "Создание учителя",
        description = "Позволяет создать нового учителя"
    )
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

    @Operation(
        summary = "Получение всех учителей",
        description = "Позволяет получить всех учителей"
    )
    @GetMapping
    public Page<ResponseTeacherDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size) {
        return service.getAll(page, size);
    }

    @Operation(
        summary = "Получение учителя по id",
        description = "Позволяет получить учителя по id"
    )
    @GetMapping("{id}")
    public ResponseTeacherDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }


    @Operation(
        summary = "Обновление учителя по id",
        description = "Позволяет обновить учителя по id"
    )
    @PutMapping("{id}")
    public ResponseTeacherDTO update(@PathVariable Long id, @Validated @RequestBody TeacherTransientDTO teacherTransientDTO) {
        return service.update(id,teacherTransientDTO);
    }

    @Operation(
        summary = "Удаление учителя по id",
        description = "Позволяет удалить учителя по id"
    )
    @DeleteMapping("{id}")
    public DeleteResponseDTO delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @Operation(
        summary = "Установление связи учитель-курс",
        description = "Позволяет установить связь учитель-курс"
    )
    @PostMapping("/{teacherId}/courses/{coursesId}")
    public ResponseCoursesDTO setRelationTeacherWithCourse(@PathVariable Long teacherId, @PathVariable Long coursesId) {
        return service.setRelationTeacherWithCourse(teacherId, coursesId);
    }

}
