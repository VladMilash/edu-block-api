package com.mvo.edublockapi.service;

import com.mvo.edublockapi.dto.StudentDTO;
import com.mvo.edublockapi.dto.StudentRegistrationDTO;

import java.util.List;

public interface StudentService {
    StudentDTO save(StudentRegistrationDTO studentRegistrationDTO);
    List<StudentDTO> getAll();
}
