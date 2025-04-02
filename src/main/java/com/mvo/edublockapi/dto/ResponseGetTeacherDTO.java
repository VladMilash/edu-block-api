package com.mvo.edublockapi.dto;

import java.util.Set;

public record ResponseGetTeacherDTO
    (Long id, String name, Set<CourseShortDTO> courses, DepartmentShortDTO department) {
}
