package com.mvo.edublockapi.service;

import com.mvo.edublockapi.dto.CourseDTO;
import com.mvo.edublockapi.dto.ResponseGetAllCourses;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;

import java.util.List;

public interface CourseService {
    CourseDTO save(CourseTransientDTO courseTransientDTO);
    List<ResponseGetAllCourses> getAll();
}
