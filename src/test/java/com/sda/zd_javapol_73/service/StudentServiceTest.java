package com.sda.zd_javapol_73.service;

import com.sda.zd_javapol_73.model.Student;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //- jeśli dodamy taką adnotację to załaduje się aplikacja spring wraz z bean'ami
public class StudentServiceTest {
    private final List<Student> TESTING_SET = Arrays.asList(
            new Student(1L, "Marian", "Kowalski", "12345678", LocalDate.of(2011, 1, 8), new HashSet<>()),
            new Student(2L, "Ola", "Nowak", "12345679", LocalDate.of(2010, 6, 30), new HashSet<>()),
            new Student(3L, "Krysia", "Grudzień", "12345680", LocalDate.of(2012, 2, 23), new HashSet<>()),
            new Student(4L, "Maria", "Stolik", "12345681", LocalDate.of(2015, 3, 17), new HashSet<>()),
            new Student(5L, "Filip", "Myszka", "12345682", LocalDate.of(2018, 7, 21), new HashSet<>()),
            new Student(6L, "Felix", "Klawiatura", "12345683", LocalDate.of(2012, 1, 11), new HashSet<>())
    );

    @Autowired
    private StudentService studentService;

    @Nested
    class StudentServiceFetchStudentsTests {
        @MockBean
        private StudentRepository studentRepository;

        @BeforeEach
        void setUp() {
            given(studentRepository.findAll()).willReturn(TESTING_SET);
        }

        @Test
        void findAll_returnsAllObjectsRetrievedFromDatabase() {
            List<StudentDto> studentDtoList = studentService.findAll();

            assertNotNull(studentDtoList);
            assertEquals(TESTING_SET.size(), studentDtoList.size());
        }

        @Test
        void findAll_properlyMapsObjectsFromDatabase() {
            List<StudentDto> studentDtoList = studentService.findAll();

            // iteruje przez wszystkie DTO
            for (StudentDto studentDto : studentDtoList) {

                // dla każdego DTO sprawdź czy istnieje obiekt w TESTING_SET który posiada pola odpowiadające tym z DTO
                assertTrue(TESTING_SET.stream().anyMatch(student -> {
                    return studentDto.getIdentifier().equals(student.getId()) &&
                            studentDto.getIndex().equals(student.getIndexNumber()) &&
                            studentDto.getName().equals(student.getFirstName()) &&
                            studentDto.getSurname().equals(student.getLastName());
                }));
            }
        }
    }

}