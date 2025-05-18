package com.mvo.edublockapi.service;

import com.mvo.edublockapi.dto.CourseShortDTO;
import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseStudentDTO;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface StudentService {
    ResponseStudentDTO save(StudentTransientDTO studentTransientDTO);
    Page<ResponseStudentDTO> getAll(int page, int size);
    ResponseStudentDTO getById(Long id);
    ResponseStudentDTO update(Long id, StudentTransientDTO studentTransientDTO);
    DeleteResponseDTO delete(Long id);
    ResponseStudentDTO setRelationWithCourse(Long studentId, Long courseId);
    Set<CourseShortDTO> getStudentCourses(Long id);

}
