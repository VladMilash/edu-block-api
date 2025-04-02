package com.mvo.edublockapi.service;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseGetStudentDTO;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;

import java.util.List;

public interface StudentService {
    ResponseGetStudentDTO save(StudentTransientDTO studentTransientDTO);
    List<ResponseGetStudentDTO> getAll();
    ResponseGetStudentDTO getById(Long id);
    ResponseGetStudentDTO update(Long id, StudentTransientDTO studentTransientDTO);
    DeleteResponseDTO delete(Long id);
    ResponseGetStudentDTO setRelationWithCourse(Long studentId, Long courseId);

}
