package com.mvo.edublockapi.dto;

import java.util.Set;

public record ResponseGetStudentDTO(Long id, String name, String email, Set<CourseShortDTO> courses) {
}
