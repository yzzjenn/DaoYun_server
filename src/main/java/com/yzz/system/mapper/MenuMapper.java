package com.yzz.system.mapper;

import com.yzz.base.BaseMapper;
import com.yzz.system.dao.MenuDto;
import com.yzz.system.pojo.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends BaseMapper<MenuDto, Menu> {

}
