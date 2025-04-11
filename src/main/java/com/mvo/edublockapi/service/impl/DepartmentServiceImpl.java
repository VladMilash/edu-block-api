package com.mvo.edublockapi.service.impl;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseDepartmentDTO;
import com.mvo.edublockapi.dto.requestdto.DepartmentTransientDTO;
import com.mvo.edublockapi.entity.Department;
import com.mvo.edublockapi.entity.Teacher;
import com.mvo.edublockapi.exception.NotFoundEntityException;
import com.mvo.edublockapi.mapper.DepartmentMapper;
import com.mvo.edublockapi.repository.DepartmentRepository;
import com.mvo.edublockapi.service.DepartmentService;
import com.mvo.edublockapi.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final TeacherService teacherService;

    @Override
    public ResponseDepartmentDTO save(DepartmentTransientDTO departmentTransientDTO) {
        log.info("Creating department with name: {}", departmentTransientDTO.name());
        Department transientDepartment = departmentMapper.fromDepartmentTransientDTO(departmentTransientDTO);
        Department persistDepartment = departmentRepository.save(transientDepartment);
        log.info("Department successfully created with id: {}", persistDepartment.getId());
        return departmentMapper.toResponseGetDepartmentDTO(persistDepartment);
    }

    @Override
    public ResponseDepartmentDTO getById(Long id) {
        Department department = getDepartmentById(id);
        log.info("Department with id: {} successfully found", id);
        return departmentMapper.toResponseGetDepartmentDTO(department);
    }

    @Override
    public List<ResponseDepartmentDTO> getAll() {
        log.info("Getting all department");
        return departmentRepository.findAll()
            .stream()
            .map(departmentMapper::toResponseGetDepartmentDTO)
            .toList();
    }

    @Override
    public DeleteResponseDTO delete(Long id) {
        log.info("Getting department by id: {} for deleted", id);
        Department department = getDepartmentById(id);
        departmentRepository.delete(department);
        log.info("Department with id: {} successfully delete", id);
        return new DeleteResponseDTO("Department deleted successfully");
    }


    @Override
    public ResponseDepartmentDTO update(Long id, DepartmentTransientDTO departmentTransientDTO) {
        log.info("Getting department by id: {} for update", id);
        Department department = getDepartmentById(id);
        log.info("Updating department with id: {}", id);
        department.setName(departmentTransientDTO.name());
        Department updatedDepartment = departmentRepository.save(department);
        log.info("Department with id: {} successfully updated", id);
        return departmentMapper.toResponseGetDepartmentDTO(updatedDepartment);
    }


    @Transactional
    @Override
    public ResponseDepartmentDTO setRelationWithTeacher(Long departmentId, Long teacherId) {
        log.info("Setting relations for department-teacher, with department id: {}, and teacher id: {}", departmentId, teacherId);
        Department department = getDepartmentById(departmentId);
        Teacher teacher = teacherService.getTeacher(teacherId);
        department.setHeadOfDepartment(teacher);
        Department updatedDepartment = departmentRepository.save(department);
        log.info("Finished setting relation for department-teacher, with department id: {}, and teacher id: {}", departmentId, teacherId);
        return departmentMapper.toResponseGetDepartmentDTO(updatedDepartment);
    }

    private Department getDepartmentById(Long id) {
        log.info("Started found fo department with id: {}", id);
        return departmentRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Department with id {} not found", id);
                return new NotFoundEntityException("Department with ID " + id + " not found");
            });
    }
}
