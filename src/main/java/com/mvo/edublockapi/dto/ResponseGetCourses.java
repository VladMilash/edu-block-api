package com.mvo.edublockapi.dto;

import java.util.Set;

public record ResponseGetCourses(Long id, String title, TeacherShortDTO teacher, Set<StudentShortDTO> students) {
}
