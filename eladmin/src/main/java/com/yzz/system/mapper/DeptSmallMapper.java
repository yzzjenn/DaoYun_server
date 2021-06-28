package com.yzz.system.mapper;

import com.yzz.base.BaseMapper;
import com.yzz.system.dao.DeptSmallDto;
import com.yzz.system.pojo.Dept;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptSmallMapper extends BaseMapper<DeptSmallDto, Dept> {

}