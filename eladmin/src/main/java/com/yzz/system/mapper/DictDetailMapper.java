package com.yzz.system.mapper;

import com.yzz.base.BaseMapper;
import com.yzz.system.dao.DictDetailDto;
import com.yzz.system.pojo.DictDetail;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {DictSmallMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictDetailMapper extends BaseMapper<DictDetailDto, DictDetail> {

}