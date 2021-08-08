package com.sda.zd_javapol_73.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql ma auto_increment
    private Long id;

    @Enumerated(EnumType.STRING)
    private GradeSubject subject;

    private double gradeValue;

    @ManyToOne()
    private Student student;

}
