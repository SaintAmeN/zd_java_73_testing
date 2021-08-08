package com.sda.zd_javapol_73.service;

import com.sda.zd_javapol_73.model.Grade;
import com.sda.zd_javapol_73.model.Student;
import com.sda.zd_javapol_73.model.dto.StudentDto;
import com.sda.zd_javapol_73.repository.GradeRepository;
import com.sda.zd_javapol_73.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

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
    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private GradeRepository gradeRepository;

    @Autowired
    private StudentService studentService;

    @Nested
    class StudentServiceFetchStudentsTests {
        @BeforeEach
        void setUp() {
            // studentRepository.findAll() - obiekt i funkcja która ma być imitowana/sztuczna
            given(studentRepository.findAll())
                    .willReturn(TESTING_SET);
        }

        @Test
        void findAll_repositoryIsCalledOneTime() {
            // test weryfikujący że funkcja findAll jest wywoływana 1 raz.
            studentService.findAll();

            verify(studentRepository, times(1)).findAll();
        }

        @Test
        void findAll_returnsAllObjectsRetrievedFromDatabase() {
            List<StudentDto> studentDtoList = studentService.findAll();

            verify(studentRepository, times(1)).findAll();
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

    public static Stream<Arguments> getGradesToCalculateAverage() {
        return Stream.of(
                Arguments.of(Arrays.asList(new Grade(null, null, 5.0, null), new Grade(null, null, 5.0, null)), 5.0),
                Arguments.of(Arrays.asList(new Grade(null, null, 1.0, null), new Grade(null, null, 5.0, null)), 3.0),
                Arguments.of(Arrays.asList(new Grade(null, null, 2.0, null), new Grade(null, null, 4.0, null)), 3.0),
                Arguments.of(Arrays.asList(new Grade(null, null, 0, null), new Grade(null, null, 5.0, null)), 2.5)
        );
    }

    @Nested
    class StudentServiceGradesCalculationTests {

        @Test
        void caluclateGradesAverage_handlesNoAverage() {
            final long TEST_STUDENT_ID = 1L;
            final Student testStudent = new Student(TEST_STUDENT_ID, "Marian", "Kowalski", "12345678", LocalDate.of(2011, 1, 8), new HashSet<>());

            given(studentRepository.findById(TEST_STUDENT_ID)).willReturn(Optional.of(testStudent));

            OptionalDouble optionalDouble = studentService.calculateGradesAverage(TEST_STUDENT_ID);
            assertFalse(optionalDouble.isPresent());
        }

        @Test()
        void caluclateGradesAverage_throwsExceptionIfGivenStudentDoesNotExist() {
            final long TEST_STUDENT_ID = 2L;
            given(studentRepository.findById(TEST_STUDENT_ID)).willReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> {
                studentService.calculateGradesAverage(2L);
            });
        }
    }

    @ParameterizedTest
    @MethodSource(value = "getGradesToCalculateAverage")
    void caluclateGradesAverage_properlyCalculatesAverage(List<Grade> grades, double expectedAverage) {
        final long TEST_STUDENT_ID = 3L;
        final Student testStudent = new Student(TEST_STUDENT_ID,
                "Marian",
                "Kowalski",
                "12345678",
                LocalDate.of(2011, 1, 8),
                new HashSet<>(grades));
        given(studentRepository.findById(TEST_STUDENT_ID)).willReturn(Optional.of(testStudent));
        given(gradeRepository.findAllByStudent(testStudent)).willReturn(grades);

        OptionalDouble optionalAverage = studentService.calculateGradesAverage(TEST_STUDENT_ID);
        assertTrue(optionalAverage.isPresent());
        assertEquals(expectedAverage, optionalAverage.getAsDouble());

        // jeśli spodziewamy się jakiegoś błędu obliczeń z akceptowalnym marginesem, to podajemy go jako 3 parametr.
        assertEquals(expectedAverage, optionalAverage.getAsDouble(), 0.001);
    }
}