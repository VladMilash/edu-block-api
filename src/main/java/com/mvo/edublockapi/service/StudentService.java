package com.mvo.edublockapi.service;

import com.mvo.edublockapi.dto.CourseShortDTO;
import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseStudentDTO;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;

import java.util.List;
import java.util.Set;

public interface StudentService {
    ResponseStudentDTO save(StudentTransientDTO studentTransientDTO);
    List<ResponseStudentDTO> getAll();
    ResponseStudentDTO getById(Long id);
    ResponseStudentDTO update(Long id, StudentTransientDTO studentTransientDTO);
    DeleteResponseDTO delete(Long id);
    ResponseStudentDTO setRelationWithCourse(Long studentId, Long courseId);
    Set<CourseShortDTO> getStudentCourses(Long id);

}
