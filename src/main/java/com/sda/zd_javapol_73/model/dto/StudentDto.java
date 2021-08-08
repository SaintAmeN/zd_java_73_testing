package com.sda.zd_javapol_73.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long identifier;
    private String name;
    private String surname;
    private String index;
}
