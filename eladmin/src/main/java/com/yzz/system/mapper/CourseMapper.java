package com.yzz.system.mapper;

import com.yzz.base.BaseMapper;
import com.yzz.system.dao.CourseDto;
import com.yzz.system.pojo.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper extends BaseMapper<CourseDto, Course> {
    @Override
    @Mapping(source = "course.createUser.nickName", target = "userName")
    CourseDto toDto(Course course);
}