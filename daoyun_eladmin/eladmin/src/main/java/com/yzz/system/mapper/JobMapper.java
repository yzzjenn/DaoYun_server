package com.yzz.system.mapper;

import com.yzz.base.BaseMapper;

import com.yzz.system.dao.JobDto;
import com.yzz.system.pojo.Job;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper extends BaseMapper<JobDto, Job> {
}
