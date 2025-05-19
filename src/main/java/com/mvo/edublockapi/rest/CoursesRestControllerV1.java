package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseCoursesDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
import com.mvo.edublockapi.service.CourseService;
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
@RequestMapping("api/v1/courses/")
public class CoursesRestControllerV1 {
    private final CourseService courseService;

    @Operation(
        summary = "Создание курса",
        description = "Позволяет создать новый курс"
    )
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

    @Operation(
        summary = "Получение всех курсов",
        description = "Позволяет получить все курсы"
    )
    @GetMapping
    public Page<ResponseCoursesDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size) {
        return courseService.getAll(page, size);
    }

    @Operation(
        summary = "Получение курса по id",
        description = "Позволяет получить курс по id"
    )
    @GetMapping("{id}")
    public ResponseCoursesDTO getById(@PathVariable long id) {
        return courseService.getById(id);
    }

    @Operation(
        summary = "Обновление курса по id",
        description = "Позволяет обновить курс по id"
    )
    @PutMapping("{id}")
    public ResponseCoursesDTO update(@PathVariable Long id, @Validated @RequestBody CourseTransientDTO courseTransientDTO) {
        return courseService.update(id, courseTransientDTO);
    }

    @Operation(
        summary = "Удаление курса по id",
        description = "Позволяет удалить курс по id"
    )
    @DeleteMapping("{id}")
    public DeleteResponseDTO delete(@PathVariable Long id) {
        return courseService.delete(id);
    }
}
