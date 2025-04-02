package com.mvo.edublockapi.mapper;

import com.mvo.edublockapi.dto.CourseShortDTO;
import com.mvo.edublockapi.dto.DepartmentShortDTO;
import com.mvo.edublockapi.dto.ResponseGetTeacherDTO;
import com.mvo.edublockapi.dto.TeacherShortDTO;
import com.mvo.edublockapi.dto.requestdto.TeacherTransientDTO;
import com.mvo.edublockapi.entity.Teacher;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    default ResponseGetTeacherDTO toResponseGetTeacherDTO(Teacher teacher) {
        return new ResponseGetTeacherDTO(
            teacher.getId(),
            teacher.getName(),
            teacher.getCourses()
                .stream()
                .map(course -> new CourseShortDTO(course.getId(), course.getTitle(),
                    new TeacherShortDTO(course.getTeacher().getId(), course.getTeacher().getName())))
                .collect(Collectors.toSet()),
            teacher.getDepartment() != null
                ? new DepartmentShortDTO(teacher.getDepartment().getId(), teacher.getDepartment().getName())
                : null
        );
    }

    default Teacher fromTeacherTransientDTO(TeacherTransientDTO teacherTransientDTO) {
        Teacher teacher = new Teacher();
        teacher.setName(teacherTransientDTO.name());
        return teacher;
    }

}
