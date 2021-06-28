package com.yzz.system.mapper;

import com.yzz.base.BaseMapper;
import com.yzz.system.dao.StudentDto;
import com.yzz.system.pojo.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper extends BaseMapper<StudentDto, Student> {

    @Mapping(source = "experience", target = "experience")
    StudentDto toDto(Student entity, Integer experience);
}
