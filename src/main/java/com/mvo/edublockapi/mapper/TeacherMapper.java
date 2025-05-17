package com.mvo.edublockapi.mapper;

import com.mvo.edublockapi.dto.CourseShortDTO;
import com.mvo.edublockapi.dto.DepartmentShortDTO;
import com.mvo.edublockapi.dto.ResponseTeacherDTO;
import com.mvo.edublockapi.dto.requestdto.TeacherTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.entity.Teacher;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface TeacherMapper extends MapperConfig {

    default ResponseTeacherDTO toResponseGetTeacherDTO(Teacher teacher) {
        return new ResponseTeacherDTO(
            teacher.getId(),
            teacher.getName(),
            teacher.getCourses()
                .stream()
                .map(course -> new CourseShortDTO(course.getId(), course.getTitle(),
                    getTeacherShortDTO(course, Course::getTeacher)))
                .collect(Collectors.toSet()),
            getDepartment(teacher)
        );
    }

    private static DepartmentShortDTO getDepartment(Teacher teacher) {
        return teacher.getDepartment() != null
            ? new DepartmentShortDTO(teacher.getDepartment().getId(), teacher.getDepartment().getName())
            : null;
    }

    default Teacher fromTeacherTransientDTO(TeacherTransientDTO teacherTransientDTO) {
        Teacher teacher = new Teacher();
        teacher.setName(teacherTransientDTO.name());
        return teacher;
    }


}
