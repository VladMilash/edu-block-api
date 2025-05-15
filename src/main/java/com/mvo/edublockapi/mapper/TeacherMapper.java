package com.mvo.edublockapi.mapper;

import com.mvo.edublockapi.dto.CourseShortDTO;
import com.mvo.edublockapi.dto.DepartmentShortDTO;
import com.mvo.edublockapi.dto.ResponseTeacherDTO;
import com.mvo.edublockapi.dto.TeacherShortDTO;
import com.mvo.edublockapi.dto.requestdto.TeacherTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.entity.Teacher;
import org.mapstruct.Mapper;

import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    default ResponseTeacherDTO toResponseGetTeacherDTO(Teacher teacher) {
        return new ResponseTeacherDTO(
            teacher.getId(),
            teacher.getName(),
            teacher.getCourses()
                .stream()
                .map(course -> {
                    return new CourseShortDTO(course.getId(), course.getTitle(),
                        getTeacherShortDTO(course, Course::getTeacher));
                })
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

    static <T> TeacherShortDTO getTeacherShortDTO(T source, Function<T, Teacher> function) {
        Teacher teacher = function.apply(source);
        return teacher != null
            ?
            new TeacherShortDTO(
                teacher.getId(),
                teacher.getName())
            : null;
    }


}
