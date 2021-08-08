package com.sda.zd_javapol_73.repository;

import com.sda.zd_javapol_73.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
