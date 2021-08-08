package com.sda.zd_javapol_73.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql ma auto_increment
    private Long id;

    private String firstName;
    private String lastName;
    private String indexNumber;
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "student")
    private Set<Grade> grades;
}
