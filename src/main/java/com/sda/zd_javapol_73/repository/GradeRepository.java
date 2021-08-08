package com.sda.zd_javapol_73.repository;

import com.sda.zd_javapol_73.model.Grade;
import com.sda.zd_javapol_73.model.GradeSubject;
import com.sda.zd_javapol_73.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// JPA - Java Persistence API (Application Programming Interface) -> Definicja funkcjonalności jaką Java posiada w komunikacji z Bazą Danych
//      - interfejs - mówi jakie są funkcje/metody interfejsy/klasy
//
// Data Access Object - komponent (repository) odpowiada za wykonywanie zapytań do bazy
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByStudent(Student student);

    // każda metoda Springa wygeneruje automatycznie zapytanie SQL
    List<Grade> findAllByGradeValueAndSubject(double grade, GradeSubject subject);

//    @Query(/*...*/)
//    List<Grade> findAllByGradeValueAndSubject(double grade, GradeSubject subject);
}
