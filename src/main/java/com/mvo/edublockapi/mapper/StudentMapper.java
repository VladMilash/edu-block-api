package com.mvo.edublockapi.mapper;

import com.mvo.edublockapi.dto.CourseShortDTO;
import com.mvo.edublockapi.dto.ResponseGetStudentDTO;
import com.mvo.edublockapi.dto.StudentDTO;
import com.mvo.edublockapi.dto.TeacherShortDTO;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;
import com.mvo.edublockapi.entity.Student;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Student fromStudentTransientDTO(StudentTransientDTO studentTransientDTO);

    StudentDTO map(Student student);

    @InheritInverseConfiguration
    Student map(StudentDTO studentDTO);

    default ResponseGetStudentDTO toResponseGetStudentDTO(Student student) {
        return new ResponseGetStudentDTO(
            student.getId(),
            student.getName(),
            student.getEmail(),
            student.getCourses()
                .stream()
                .map(course ->
                    new CourseShortDTO(
                        course.getId(),
                        course.getTitle(),
                        course.getTeacher() != null
                            ?
                        new TeacherShortDTO(
                            course.getTeacher().getId(),
                            course.getTeacher().getName())
                            : null
                    )
                ).collect(Collectors.toSet())
        );
    }
}
