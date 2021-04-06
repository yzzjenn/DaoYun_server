package com.yzz.system.mapper;


import com.yzz.base.BaseMapper;

import com.yzz.system.dao.UserDto;
import com.yzz.system.pojo.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {RoleMapper.class, DeptMapper.class, JobMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserDto, User> {
}
