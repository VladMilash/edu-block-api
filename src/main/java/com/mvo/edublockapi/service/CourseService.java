package com.mvo.edublockapi.service;

import com.mvo.edublockapi.dto.CourseDTO;
import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseGetCoursesDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.entity.Student;

import java.util.List;

public interface CourseService {
    ResponseGetCoursesDTO save(CourseTransientDTO courseTransientDTO);
    List<ResponseGetCoursesDTO> getAll();
    ResponseGetCoursesDTO getById(Long id);
    ResponseGetCoursesDTO update(Long id, CourseTransientDTO courseTransientDTO);
    DeleteResponseDTO delete(Long id);
    Course getCourseById(Long id);
    void setRelationWithStudent(Long courseId , Student student);
}
