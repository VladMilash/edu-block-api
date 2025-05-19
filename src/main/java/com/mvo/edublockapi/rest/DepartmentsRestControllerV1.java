package com.mvo.edublockapi.rest;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseDepartmentDTO;
import com.mvo.edublockapi.dto.requestdto.DepartmentTransientDTO;
import com.mvo.edublockapi.service.DepartmentService;
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
@RequestMapping("api/v1/departments/")
public class DepartmentsRestControllerV1 {
    private final DepartmentService service;

    @Operation(
        summary = "Создание департамента",
        description = "Позволяет создать новый департамент"
    )
    @PostMapping
    public ResponseEntity<ResponseDepartmentDTO> saveDepartment(@Validated @RequestBody DepartmentTransientDTO departmentTransientDTO,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        ResponseDepartmentDTO responseDepartmentDTO = service.save(departmentTransientDTO);
        URI location = uriComponentsBuilder
            .path("api/v1/departments/{id}")
            .buildAndExpand(responseDepartmentDTO.id())
            .toUri();
        return ResponseEntity
            .created(location)
            .body(responseDepartmentDTO);
    }

    @Operation(
        summary = "Получение всех департаментов",
        description = "Позволяет получить все департаменты"
    )
    @GetMapping
    public Page<ResponseDepartmentDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size) {
        return service.getAll(page, size);
    }

    @Operation(
        summary = "Получение департамента по id",
        description = "Позволяет получить департамент по id"
    )
    @GetMapping("{id}")
    public ResponseDepartmentDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(
        summary = "Обновление департамента по id",
        description = "Позволяет обновить департамент по id"
    )
    @PutMapping("{id}")
    public ResponseDepartmentDTO update(@PathVariable Long id, @Validated @RequestBody DepartmentTransientDTO departmentTransientDTO) {
        return service.update(id, departmentTransientDTO);
    }

    @Operation(
        summary = "Удаление департамента по id",
        description = "Позволяет удалить департамент по id"
    )
    @DeleteMapping("{id}")
    public DeleteResponseDTO delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @Operation(
        summary = "Установление связи департамент-учитель",
        description = "Позволяет установить связь департамент-учитель"
    )
    @PostMapping("{departmentId}/teacher/{teacherId}")
    public ResponseDepartmentDTO setRelationWitTeacher(@PathVariable Long departmentId, @PathVariable Long teacherId) {
        return service.setRelationWithTeacher(departmentId, teacherId);
    }

}
