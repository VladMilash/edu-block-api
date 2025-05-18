package com.mvo.edublockapi.service;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseCoursesDTO;
import com.mvo.edublockapi.dto.ResponseTeacherDTO;
import com.mvo.edublockapi.dto.requestdto.TeacherTransientDTO;
import com.mvo.edublockapi.entity.Teacher;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TeacherService {
    ResponseTeacherDTO save(TeacherTransientDTO teacherTransientDTO);
    ResponseTeacherDTO getById(Long id);
    Page<ResponseTeacherDTO> getAll(int page, int size);
    ResponseTeacherDTO update(Long id, TeacherTransientDTO teacherTransientDTO);
    DeleteResponseDTO delete(Long id);
    ResponseCoursesDTO setRelationTeacherWithCourse(Long teacherId, Long courseId);
    Teacher getTeacher(Long id);
}
