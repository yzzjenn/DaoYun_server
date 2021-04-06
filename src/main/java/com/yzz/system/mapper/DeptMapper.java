package com.yzz.system.mapper;


import com.yzz.base.BaseMapper;
import com.yzz.system.dao.DeptDto;
import com.yzz.system.pojo.Dept;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptMapper extends BaseMapper<DeptDto, Dept> {
}
