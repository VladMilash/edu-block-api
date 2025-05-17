package com.mvo.edublockapi.mapper;

import com.mvo.edublockapi.dto.CourseShortDTO;
import com.mvo.edublockapi.dto.ResponseStudentDTO;
import com.mvo.edublockapi.dto.StudentDTO;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.entity.Student;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface StudentMapper extends MapperConfig {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Student fromStudentTransientDTO(StudentTransientDTO studentTransientDTO);

    StudentDTO map(Student student);

    @InheritInverseConfiguration
    Student map(StudentDTO studentDTO);

    default ResponseStudentDTO toResponseGetStudentDTO(Student student) {
        return new ResponseStudentDTO(
            student.getId(),
            student.getName(),
            student.getEmail(),
            student.getCourses()
                .stream()
                .map(course ->
                    new CourseShortDTO(
                        course.getId(),
                        course.getTitle(),
                        getTeacherShortDTO(course, Course::getTeacher)
                    )
                ).collect(Collectors.toSet())
        );
    }


}
