package com.sda.zd_javapol_73.service;

import com.sda.zd_javapol_73.model.Student;
import com.sda.zd_javapol_73.model.dto.CreateStudentRequest;
import com.sda.zd_javapol_73.model.dto.StudentDto;
import com.sda.zd_javapol_73.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest //- jeśli dodamy taką adnotację to załaduje się aplikacja spring wraz z bean'ami
public class StudentServiceWithDatabaseTest {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    @Nested
    class StudentServiceFetchStudentsTests {
        private final List<CreateStudentRequest> TESTING_SET = Arrays.asList(
                new CreateStudentRequest("Marian", "Kowalski", "12345678", LocalDate.of(2011, 1, 8)),
                new CreateStudentRequest("Ola", "Nowak", "12345679", LocalDate.of(2010, 6, 30)),
                new CreateStudentRequest("Krysia", "Grudzień", "12345680", LocalDate.of(2012, 2, 23)),
                new CreateStudentRequest("Maria", "Stolik", "12345681", LocalDate.of(2015, 3, 17)),
                new CreateStudentRequest("Filip", "Myszka", "12345682", LocalDate.of(2018, 7, 21)),
                new CreateStudentRequest("Felix", "Klawiatura", "12345683", LocalDate.of(2012, 1, 11))
        );

        @BeforeEach
        void setUp() {
            studentRepository.deleteAll();
        }

        @Test
        void findAll_returnsAllObjectsRetrievedFromDatabase() {
            CreateStudentRequest request = new CreateStudentRequest("Marian", "Kowalski", "12345678", LocalDate.of(2011, 1, 8));
            studentService.addStudent(request);

            List<StudentDto> studentDtoList = studentService.findAll();

            assertNotNull(studentDtoList);
            assertEquals(1, studentDtoList.size());

            StudentDto dto = studentDtoList.get(0);
            assertEquals(request.getIndex(), dto.getIndex());
            assertEquals(request.getSurname(), dto.getSurname());
            assertEquals(request.getName(), dto.getName());
            assertNotNull(dto.getIdentifier());
        }

        @Test
        void findAll_returnsAllObjectsRetrievedFromDatabaseWithMultipleResults() {
            for (CreateStudentRequest request : TESTING_SET) {
                studentService.addStudent(request);
            }
            List<StudentDto> studentDtoList = studentService.findAll();

            assertNotNull(studentDtoList);
            assertEquals(TESTING_SET.size(), studentDtoList.size());

            // iteruje przez wszystkie DTO
            for (StudentDto studentDto : studentDtoList) {
                // dla każdego DTO sprawdź czy istnieje obiekt w TESTING_SET który posiada pola odpowiadające tym z DTO
                assertTrue(TESTING_SET.stream().anyMatch(student -> {
                    return studentDto.getIdentifier() != null &&
                            studentDto.getIndex().equals(student.getIndex()) &&
                            studentDto.getName().equals(student.getName()) &&
                            studentDto.getSurname().equals(student.getSurname());
                }));
            }
        }
    }

}