package com.mvo.edublockapi.dto;

import java.util.Set;

public record ResponseTeacherDTO
    (Long id, String name, Set<CourseShortDTO> courses, DepartmentShortDTO department) {
}
