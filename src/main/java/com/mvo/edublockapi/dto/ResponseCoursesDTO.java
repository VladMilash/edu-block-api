package com.mvo.edublockapi.dto;

import java.util.Set;

public record ResponseCoursesDTO
    (Long id, String title, TeacherShortDTO teacher, Set<StudentShortDTO> students) {
}
