package com.yzz.system.mapper;

import com.yzz.base.BaseMapper;
import com.yzz.system.dao.CouserSmallDto;
import com.yzz.system.pojo.Course;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseSmallMapper extends BaseMapper<CouserSmallDto, Course> {
}

