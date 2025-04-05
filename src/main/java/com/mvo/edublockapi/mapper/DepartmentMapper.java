package com.mvo.edublockapi.mapper;

import com.mvo.edublockapi.dto.ResponseGetDepartmentDTO;
import com.mvo.edublockapi.dto.TeacherShortDTO;
import com.mvo.edublockapi.dto.requestdto.DepartmentTransientDTO;
import com.mvo.edublockapi.entity.Department;
import com.mvo.edublockapi.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    default ResponseGetDepartmentDTO toResponseGetDepartmentDTO(Department department) {
        return new ResponseGetDepartmentDTO(
            department.getId(),
            department.getName(),
            department.getHeadOfDepartment() != null
                ? new TeacherShortDTO(department.getHeadOfDepartment().getId(), department.getHeadOfDepartment().getName())
                : null
        );
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "headOfDepartment", ignore = true)
    Department fromDepartmentTransientDTO(DepartmentTransientDTO departmentTransientDTO);
}
