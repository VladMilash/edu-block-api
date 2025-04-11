package com.mvo.edublockapi.service;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseDepartmentDTO;
import com.mvo.edublockapi.dto.requestdto.DepartmentTransientDTO;

import java.util.List;

public interface DepartmentService {
    ResponseDepartmentDTO save(DepartmentTransientDTO departmentTransientDTO);
    ResponseDepartmentDTO getById(Long id);
    List<ResponseDepartmentDTO> getAll();
    DeleteResponseDTO delete(Long id);
    ResponseDepartmentDTO update(Long id, DepartmentTransientDTO departmentTransientDTO);
    ResponseDepartmentDTO setRelationWithTeacher(Long departmentId, Long teacherId);
}
