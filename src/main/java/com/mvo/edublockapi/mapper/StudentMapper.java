package com.mvo.edublockapi.mapper;

import com.mvo.edublockapi.dto.StudentDTO;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;
import com.mvo.edublockapi.entity.Student;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Student fromStudentTransientDTO(StudentTransientDTO studentTransientDTO);

    StudentDTO map(Student student);

    @InheritInverseConfiguration
    Student map(StudentDTO studentDTO);
}
