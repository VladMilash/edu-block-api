package com.mvo.edublockapi.service;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseCoursesDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.entity.Student;
import com.mvo.edublockapi.entity.Teacher;

import java.util.List;

public interface CourseService {
    ResponseCoursesDTO save(CourseTransientDTO courseTransientDTO);
    List<ResponseCoursesDTO> getAll();
    ResponseCoursesDTO getById(Long id);
    ResponseCoursesDTO update(Long id, CourseTransientDTO courseTransientDTO);
    DeleteResponseDTO delete(Long id);
    Course getCourseById(Long id);
    void setRelationWithStudent(Long courseId , Student student);
    ResponseCoursesDTO setRelationWithTeacher(Long courseId, Teacher teacher);
}
