package com.mvo.edublockapi.service;

import com.mvo.edublockapi.dto.ResponseGetTeacherDTO;
import com.mvo.edublockapi.dto.requestdto.TeacherTransientDTO;

public interface TeacherService {
    ResponseGetTeacherDTO save(TeacherTransientDTO teacherTransientDTO);
}
