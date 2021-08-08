package com.sda.zd_javapol_73.model.mapper;

import com.sda.zd_javapol_73.model.Student;
import com.sda.zd_javapol_73.model.dto.CreateStudentRequest;
import com.sda.zd_javapol_73.model.dto.StudentDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mappings({
            @Mapping(target = "firstName", source = "name"),
            @Mapping(target = "lastName", source = "surname"),
            @Mapping(target = "indexNumber", source = "index"),
            @Mapping(target = "dateOfBirth", source = "brithDate"),
    })
    Student mapCreateRequestToStudent(CreateStudentRequest request);

    // ---------------------------------------------------------------
    @Mappings({
            @Mapping(target = "identifier", source = "id"),
            @Mapping(target = "name", source = "firstName"),
            @Mapping(target = "surname", source = "lastName"),
            @Mapping(target = "index", source = "indexNumber"),
    })
    StudentDto mapStudentToStudentDto(Student source);
}
