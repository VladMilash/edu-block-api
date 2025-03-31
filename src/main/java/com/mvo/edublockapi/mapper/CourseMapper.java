package com.mvo.edublockapi.mapper;

import com.mvo.edublockapi.dto.CourseDTO;
import com.mvo.edublockapi.dto.ResponseGetCourses;
import com.mvo.edublockapi.dto.StudentShortDTO;
import com.mvo.edublockapi.dto.TeacherShortDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.entity.Teacher;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "students", ignore = true)
    Course fromCourseTransientDTO(CourseTransientDTO courseTransientDTO);

    Course map(CourseDTO courseDTO);

    @InheritInverseConfiguration
    CourseDTO map(Course course);

    default ResponseGetCourses toResponseGetCourses(Course course) {
        return new ResponseGetCourses(
            course.getId(),
            course.getTitle(),
            course.getTeacher() != null
                ? new TeacherShortDTO(course.getTeacher().getId(), course.getTeacher().getName())
                : null,
            course.getStudents().stream()
                .map(student -> new StudentShortDTO(
                    student.getId(),
                    student.getName()
                )).collect(Collectors.toSet())
        );
    }
}
