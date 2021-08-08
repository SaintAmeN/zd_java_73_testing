package com.sda.zd_javapol_73.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@ApiModel(description = "Data transfer object request to create student.")
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentRequest {

    @ApiModelProperty(name = "name", required = true,
            value = "First name of student entity.",
            example = "Jan")
    private String name;

    @ApiModelProperty(name = "surname", required = true,
            value = "Last name of student entity.",
            example = "Kowalski")
    private String surname;

    @ApiModelProperty(name = "index", required = true,
            value = "Student identification index of the entity. Should be a number value.",
            example = "123123")
    private String index;

    private LocalDate brithDate;
}
