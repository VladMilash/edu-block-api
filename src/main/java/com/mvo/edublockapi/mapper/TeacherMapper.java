package com.mvo.edublockapi.mapper;

import com.mvo.edublockapi.dto.TeacherShortDTO;
import com.mvo.edublockapi.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

//    @Mapping(target = "courses", ignore = true)
//    @Mapping(target = "department", ignore = true)
    TeacherShortDTO toTeacherShortDTO(Teacher teacher);

}
