package com.mvo.edublockapi.service.impl;

import com.mvo.edublockapi.dto.ResponseGetStudentDTO;
import com.mvo.edublockapi.dto.ResponseGetTeacherDTO;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;
import com.mvo.edublockapi.dto.requestdto.TeacherTransientDTO;
import com.mvo.edublockapi.entity.Student;
import com.mvo.edublockapi.entity.Teacher;
import com.mvo.edublockapi.mapper.TeacherMapper;
import com.mvo.edublockapi.repository.TeacherRepository;
import com.mvo.edublockapi.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public ResponseGetTeacherDTO save(TeacherTransientDTO teacherTransientDTO) {
        log.info("Creating teacher with name: {}", teacherTransientDTO.name());
        Teacher transientTeacher = teacherMapper.fromTeacherTransientDTO(teacherTransientDTO);
        Teacher persistTeacher = teacherRepository.save(transientTeacher);
        log.info("Teacher successfully created with id: {}", persistTeacher.getId());
        persistTeacher.setCourses(new HashSet<>());
        return teacherMapper.toResponseGetTeacherDTO(persistTeacher);
    }
}
