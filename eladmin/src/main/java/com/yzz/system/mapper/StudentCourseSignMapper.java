package com.yzz.system.mapper;

import com.yzz.base.BaseMapper;
import com.yzz.system.dao.StudentCourseSignDto;
import com.yzz.system.pojo.StudentCourseSign;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentCourseSignMapper extends BaseMapper<StudentCourseSignDto, StudentCourseSign> {

}
