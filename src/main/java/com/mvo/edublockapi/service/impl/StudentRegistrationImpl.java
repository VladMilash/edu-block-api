package com.mvo.edublockapi.service.impl;

import com.mvo.edublockapi.dto.StudentDTO;
import com.mvo.edublockapi.dto.StudentRegistrationDTO;
import com.mvo.edublockapi.entity.Student;
import com.mvo.edublockapi.mapper.StudentMapper;
import com.mvo.edublockapi.repository.StudentRepository;
import com.mvo.edublockapi.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentRegistrationImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentDTO save(StudentRegistrationDTO studentRegistrationDTO) {
        Student transientStudent = studentMapper.fromStudentRegistrationDTO(studentRegistrationDTO);
        Student persistStudent = studentRepository.save(transientStudent);
        persistStudent.setCourses(new HashSet<>());
        return studentMapper.map(persistStudent);
    }

    @Override
    public List<StudentDTO> getAll() {
        return studentRepository.findAll()
            .stream()
            .map(studentMapper::map)
            .toList();
    }
}
