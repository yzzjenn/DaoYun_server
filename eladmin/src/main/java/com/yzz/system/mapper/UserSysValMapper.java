package com.yzz.system.mapper;

import com.yzz.base.BaseMapper;
import com.yzz.system.dao.UserSysValDto;
import com.yzz.system.pojo.UserSysVal;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserSysValMapper extends BaseMapper<UserSysValDto, UserSysVal> {

}
