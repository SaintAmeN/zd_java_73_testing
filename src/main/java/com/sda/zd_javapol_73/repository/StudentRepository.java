package com.sda.zd_javapol_73.repository;

import com.sda.zd_javapol_73.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByDateOfBirthBetween(LocalDate from, LocalDate to);
}
