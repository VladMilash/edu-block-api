package com.mvo.edublockapi.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mvo.edublockapi.entity.Course;

import java.util.Set;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record StudentDTO(Long id, String name, String email, Set<Course> courses) {
}
