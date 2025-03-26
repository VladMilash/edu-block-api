package com.mvo.edublockapi.mapper;

import com.mvo.edublockapi.dto.CourseDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
import com.mvo.edublockapi.entity.Course;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "students", ignore = true)
    Course fromCourseTransientDTO(CourseTransientDTO courseTransientDTO);

    Course map(CourseDTO courseDTO);

    @InheritInverseConfiguration
    CourseDTO map(Course course);
}
