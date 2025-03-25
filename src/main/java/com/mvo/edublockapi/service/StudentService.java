package com.mvo.edublockapi.service;

import com.mvo.edublockapi.dto.StudentDTO;
import com.mvo.edublockapi.dto.StudentTransientDTO;

import java.util.List;

public interface StudentService {
    StudentDTO save(StudentTransientDTO studentTransientDTO);
    List<StudentDTO> getAll();
    StudentDTO getById(Long id);
    StudentDTO update(Long id, StudentTransientDTO studentTransientDTO);
}
