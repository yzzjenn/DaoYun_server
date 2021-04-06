package com.yzz.system.mapper;

import com.yzz.base.BaseMapper;

import com.yzz.system.dao.RoleSmallDto;
import com.yzz.system.pojo.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleSmallMapper extends BaseMapper<RoleSmallDto, Role> {

}
