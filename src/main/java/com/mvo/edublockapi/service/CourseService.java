package com.mvo.edublockapi.service;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseCoursesDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.entity.Student;
import com.mvo.edublockapi.entity.Teacher;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {
    ResponseCoursesDTO save(CourseTransientDTO courseTransientDTO);
    Page<ResponseCoursesDTO> getAll(int page, int size);
    ResponseCoursesDTO getById(Long id);
    ResponseCoursesDTO update(Long id, CourseTransientDTO courseTransientDTO);
    DeleteResponseDTO delete(Long id);
    Course getCourseById(Long id);
    void setRelationWithStudent(Course course, Student student);
    ResponseCoursesDTO setRelationWithTeacher(Course course, Teacher teacher);
}
