package com.mvo.edublockapi.service;

import com.mvo.edublockapi.dto.CourseDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;

public interface CourseService {
    CourseDTO save(CourseTransientDTO courseTransientDTO);
}
