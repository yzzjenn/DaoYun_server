package com.eladmin.mapper;

import com.common.base.BaseMapper;
import com.eladmin.dao.JobDto;
import com.eladmin.pojo.Job;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper extends BaseMapper<JobDto, Job> {
}
