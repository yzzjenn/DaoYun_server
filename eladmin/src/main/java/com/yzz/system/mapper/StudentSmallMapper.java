package com.yzz.system.mapper;

import com.yzz.base.BaseMapper;
import com.yzz.system.dao.StudentSmallDto;
import com.yzz.system.pojo.Student;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentSmallMapper extends BaseMapper<StudentSmallDto, Student> {
}
