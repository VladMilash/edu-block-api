package com.mvo.edublockapi.dto;

import java.util.Set;

public record ResponseStudentDTO(Long id, String name, String email, Set<CourseShortDTO> courses) {
}
