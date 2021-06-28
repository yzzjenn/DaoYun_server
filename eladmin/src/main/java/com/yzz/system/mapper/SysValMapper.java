package com.yzz.system.mapper;

import com.yzz.base.BaseMapper;
import com.yzz.system.dao.DictDto;
import com.yzz.system.dao.SysValDto;
import com.yzz.system.pojo.Dict;
import com.yzz.system.pojo.SysVal;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysValMapper extends BaseMapper<SysValDto, SysVal> {
}
