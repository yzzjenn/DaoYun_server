package com.yzz.system.mapper;

import com.yzz.base.BaseMapper;
import com.yzz.system.dao.SignHistoryDto;
import com.yzz.system.pojo.SignHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SignHistoryMapper extends BaseMapper<SignHistoryDto, SignHistory> {

    @Override
    @Mappings({
            @Mapping(source = "entity.course.courseName", target = "courseName"),
            @Mapping(source = "entity.course.courseCode", target = "courseCode"),
            @Mapping(source = "entity.course.college.name", target = "collegeName"),
            @Mapping(source = "entity.course.teacherName", target = "teacherName")
    })
    SignHistoryDto toDto(SignHistory entity);
}
