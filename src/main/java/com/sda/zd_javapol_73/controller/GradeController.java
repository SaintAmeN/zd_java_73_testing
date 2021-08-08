package com.sda.zd_javapol_73.controller;

import com.sda.zd_javapol_73.model.Grade;
import com.sda.zd_javapol_73.model.Student;
import com.sda.zd_javapol_73.service.GradeService;
import com.sda.zd_javapol_73.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@Slf4j
@RestController                     // json objects / REST / API
@RequestMapping("/api/grade")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @GetMapping("/{studentId}")
    public List<Grade> getStudentGrades(@PathVariable(name = "studentId") Long studentId){
        return gradeService.findAllGradesByStudentId(studentId);
    }
}
