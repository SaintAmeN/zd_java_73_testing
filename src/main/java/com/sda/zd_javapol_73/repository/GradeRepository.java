package com.sda.zd_javapol_73.repository;

import com.sda.zd_javapol_73.model.Grade;
import com.sda.zd_javapol_73.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByStudent(Student student);
}
