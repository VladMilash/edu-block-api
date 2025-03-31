package com.mvo.edublockapi.dto;

import java.util.Set;

public record ResponseGetCoursesDTO(Long id, String title, TeacherShortDTO teacher, Set<StudentShortDTO> students) {
}
