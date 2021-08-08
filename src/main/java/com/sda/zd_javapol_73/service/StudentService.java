package com.sda.zd_javapol_73.service;

import com.sda.zd_javapol_73.model.Student;
import com.sda.zd_javapol_73.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> findAll(){
        return studentRepository.findAll();
    }
}
