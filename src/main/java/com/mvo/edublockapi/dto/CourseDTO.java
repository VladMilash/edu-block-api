package com.mvo.edublockapi.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mvo.edublockapi.entity.Student;
import com.mvo.edublockapi.entity.Teacher;

import java.util.Set;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CourseDTO(Long id, String title, Teacher teacher, Set<Student> students) {
}
