package com.yzz.system.mapper;

import com.yzz.base.BaseMapper;
import com.yzz.system.dao.DictSmallDto;
import com.yzz.system.pojo.Dict;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictSmallMapper extends BaseMapper<DictSmallDto, Dict> {

}
