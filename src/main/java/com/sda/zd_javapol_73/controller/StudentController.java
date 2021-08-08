package com.sda.zd_javapol_73.controller;

import com.sda.zd_javapol_73.model.Student;
import com.sda.zd_javapol_73.model.dto.CreateStudentRequest;
import com.sda.zd_javapol_73.model.dto.StudentDto;
import com.sda.zd_javapol_73.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController                     // json objects / REST / API
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping()
    public List<StudentDto> getListOfStudents(){
        return studentService.findAll();
    }

    @PostMapping()
    public StudentDto addStudent(@RequestBody CreateStudentRequest request){
        return studentService.addStudent(request);
    }
}
