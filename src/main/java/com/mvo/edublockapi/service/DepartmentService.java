package com.mvo.edublockapi.service;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseGetDepartmentDTO;
import com.mvo.edublockapi.dto.requestdto.DepartmentTransientDTO;

import java.util.List;

public interface DepartmentService {
    ResponseGetDepartmentDTO save(DepartmentTransientDTO departmentTransientDTO);
    ResponseGetDepartmentDTO getById(Long id);
    List<ResponseGetDepartmentDTO> getAll();
    DeleteResponseDTO delete(Long id);
    ResponseGetDepartmentDTO update(Long id, DepartmentTransientDTO departmentTransientDTO);
    ResponseGetDepartmentDTO setRelationWithTeacher(Long departmentId, Long teacherId);
}
