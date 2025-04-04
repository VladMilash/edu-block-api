package com.mvo.edublockapi.service;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseGetTeacherDTO;
import com.mvo.edublockapi.dto.requestdto.TeacherTransientDTO;

import java.util.List;

public interface TeacherService {
    ResponseGetTeacherDTO save(TeacherTransientDTO teacherTransientDTO);
    ResponseGetTeacherDTO getById(Long id);
    List<ResponseGetTeacherDTO> getAll();
    ResponseGetTeacherDTO update(Long id, TeacherTransientDTO teacherTransientDTO);
    DeleteResponseDTO delete(Long id);
}
