package com.mvo.edublockapi.mapper;

import com.mvo.edublockapi.dto.TeacherShortDTO;
import com.mvo.edublockapi.entity.Teacher;

import java.util.function.Function;

@org.mapstruct.MapperConfig(
    componentModel = "spring"
)
public interface MapperConfig {

    default  <T> TeacherShortDTO getTeacherShortDTO(T source, Function<T, Teacher> function) {
        Teacher teacher = function.apply(source);
        return teacher != null
            ?
            new TeacherShortDTO(
                teacher.getId(),
                teacher.getName())
            : null;
    }
}
