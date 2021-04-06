package com.yzz.system.mapper;

import com.yzz.base.BaseMapper;

import com.yzz.system.dao.RoleDto;
import com.yzz.system.pojo.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {MenuMapper.class, DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends BaseMapper<RoleDto, Role> {

}
