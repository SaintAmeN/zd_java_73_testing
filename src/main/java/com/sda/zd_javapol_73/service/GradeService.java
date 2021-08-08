package com.sda.zd_javapol_73.service;

import com.sda.zd_javapol_73.model.Grade;
import com.sda.zd_javapol_73.model.Student;
import com.sda.zd_javapol_73.repository.GradeRepository;
import com.sda.zd_javapol_73.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;

    public List<Grade> findAllGradesByStudentId(Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            return gradeRepository.findAllByStudent(student);
        }

        throw new EntityNotFoundException("Student with given id[" + studentId + "] does not exist!");
    }
}
