package com.sda.zd_javapol_73.service;

import com.sda.zd_javapol_73.model.Student;
import com.sda.zd_javapol_73.model.dto.CreateStudentRequest;
import com.sda.zd_javapol_73.model.dto.StudentDto;
import com.sda.zd_javapol_73.model.mapper.StudentMapper;
import com.sda.zd_javapol_73.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public List<StudentDto> findAll(){
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::mapStudentToStudentDto)
                .collect(Collectors.toList());
    }

    public StudentDto addStudent(CreateStudentRequest request) {
        log.info("Creating student from request: " + request);
        // mapowanie obiektu CreateStudentRequest(DTO) na Student
        Student student = studentMapper.mapCreateRequestToStudent(request);

        student = studentRepository.save(student);
        return studentMapper.mapStudentToStudentDto(student);
    }
}
