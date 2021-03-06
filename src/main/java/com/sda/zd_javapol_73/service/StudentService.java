package com.sda.zd_javapol_73.service;

import com.sda.zd_javapol_73.model.Grade;
import com.sda.zd_javapol_73.model.Student;
import com.sda.zd_javapol_73.model.dto.CreateStudentRequest;
import com.sda.zd_javapol_73.model.dto.StudentDto;
import com.sda.zd_javapol_73.model.mapper.StudentMapper;
import com.sda.zd_javapol_73.repository.GradeRepository;
import com.sda.zd_javapol_73.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
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

    public OptionalDouble calculateGradesAverage(Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if(studentOptional.isPresent()){
            Student student = studentOptional.get();

            List<Grade> grades = gradeRepository.findAllByStudent(student);
            return grades.stream().mapToDouble(Grade::getGradeValue).average();
        }
        throw new EntityNotFoundException("Student with given id[" + studentId + "] does not exist!");
    }

    //  - szuka?? uczni??w po roczniku (data urodzenia m??wi w kt??rej jeste??my klasie)
    // typ zwracany - List<Student)
    // parametr - rok / liczba ca??kowita / 4 cyfry
    // nie ma sytuacji wyj??tkowych z brakiem student??w
    // je??li u??ytkownik poda ujemny rok / rok w przysz??o??ci, to rzucamy wyj??tek
    //              - wyj??tek nazywamy InvalidInputException (musimy stworzy??)
    // wyszukiwanie w bazie danych dla wej??cia 1995
    // findAllByDateOfBirthBetween( FROM, TO )
    //    FROM = LocalDate.of(year, 1, 1);
    //    TO   = LocalDate.of(year, 12, 30);

}
