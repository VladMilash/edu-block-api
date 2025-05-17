package com.mvo.edublockapi.mapper;

import com.mvo.edublockapi.dto.CourseDTO;
import com.mvo.edublockapi.dto.ResponseCoursesDTO;
import com.mvo.edublockapi.dto.StudentShortDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
import com.mvo.edublockapi.entity.Course;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface CourseMapper extends MapperConfig {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "students", ignore = true)
    Course fromCourseTransientDTO(CourseTransientDTO courseTransientDTO);

    Course map(CourseDTO courseDTO);

    @InheritInverseConfiguration
    CourseDTO map(Course course);

    default ResponseCoursesDTO toResponseGetCourses(Course course) {
        return new ResponseCoursesDTO(
            course.getId(),
            course.getTitle(),
            getTeacherShortDTO(course, Course::getTeacher),
            course.getStudents().stream()
                .map(student -> new StudentShortDTO(
                    student.getId(),
                    student.getName()
                )).collect(Collectors.toSet())
        );
    }
}
